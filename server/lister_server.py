from BaseHTTPServer import BaseHTTPRequestHandler, HTTPServer
from datetime import datetime
import threading
import time
import sys
import json
import urllib

import mysql.connector


HOST_NAME = '0.0.0.0'
PORT = 8888


#Hack to speed up
def _bare_address_string(self):
    host, port = self.client_address[:2]
    return '%s' % host


BaseHTTPRequestHandler.address_string = \
    _bare_address_string
#End hack


#Handles the different requests from the client, and performs database queries
class TodoRequestHandler(BaseHTTPRequestHandler):
    def do_GET(self):
        request = self.path.split('/')
        userIP = self.address_string()

        #Return to avoid server crash from index out of bounds
        if len(request) <= 2:
            self.send_response(200)
            return

        #Find type of request, the second argument in URL
        #host/type
        request_type = request[1]
        print "Request from " + userIP + "..."
        print "Request type: " + request_type
        print

        #Find all parameters by splitting the third argument in URL on & character
        #host/type/param1=foo&param2=bar
        #Put all keys and values in a python dictionary for easy access
        request_params_strings = request[2].split("&")
        request_params = {}
        for param in request_params_strings:
            param = urllib.unquote(param).decode('utf8')
            key_and_value = param.split("=")
            key = key_and_value[0]
            value = key_and_value[1]
            request_params[key] = value

        #Execute the method that corresponds to the request type
        if request_type == "new_list":
            self.new_list(request_params)
        elif request_type == "new_list_item":
            self.new_list_item(request_params)
        elif request_type == "set_checked_status":
            self.set_checked_status(request_params)
        elif request_type == "create_account":
            self.create_account(request_params)
        elif request_type == "get_lists":
            self.get_lists(request_params)
        elif request_type == "login":
            self.login(request_params)
        elif request_type == "remove_list_item":
            self.remove_list_item(request_params)
        elif request_type == "share":
            self.share(request_params)

    #Handle requests of the following type:
    #host/new_list/title=Shopping&author=1&deadline=1395140712
    def new_list(self, request_params):
        title = request_params['title']
        author = request_params['author']
        last_change = datetime.now()
        if request_params['deadline'] == "":
            deadline = None
        else:
            deadline = request_params['deadline']
            deadline = datetime.strptime(deadline, '%Y-%m-%d')
        print "title: " + title
        print "author: " + author
        print "last_change: " + str(last_change)
        print "deadline: " + str(deadline)
        print
        cnx = self.connect()
        cursor = cnx.cursor()
        new_list_query = ("INSERT INTO lists (title, author, last_change, deadline)"
                          "VALUES (%s, %s, %s, %s)")
        cursor.execute(new_list_query, (title, author, last_change, deadline))

        last_insert_query = "SELECT LAST_INSERT_ID()"
        cursor.execute(last_insert_query)
        (list_id,) = cursor.fetchone()

        collaborator_query = "INSERT INTO collaborators (uid, list_id) VALUES (%s, %s)"
        cursor.execute(collaborator_query, (author, list_id))

        cnx.commit()
        cursor.close()
        cnx.close()
        print "Inserted {}, {}, {}, {} into lists.".format(title, author, last_change, deadline)
        self.respond(list_id)

    #Handle requests of the following type:
    #host/new_list_item/list_id=1&content=buy%20eggs
    def new_list_item(self, request_params):
        list_id = request_params['list_id']
        content = request_params['content']
        print "list_id: " + list_id
        print "content: " + content
        print

        cnx = self.connect()
        cursor = cnx.cursor()

        new_list_item_query = ("INSERT INTO list_items (list_id, content, checked)"
                               "VALUES (%s, %s, %s)")
        cursor.execute(new_list_item_query, (list_id, content, False))

        last_insert_query = "SELECT LAST_INSERT_ID()"
        cursor.execute(last_insert_query)
        (item_id,) = cursor.fetchone()

        last_change_query = "UPDATE lists SET last_change = NULL WHERE id = %s"
        cursor.execute(last_change_query, (list_id,))

        cnx.commit()
        cursor.close()
        cnx.close()
        print "Inserted {}, {}, {} into list_items".format(list_id, content, False)
        print "New item id:", item_id
        self.respond(item_id)

    #Handle requests of the following type:
    #host/set_checked_status/id=1&checked=false
    def set_checked_status(self, request_params):
        list_item_id = int(request_params['id'])
        checked = request_params['checked'] == 'true'
        print "id: " + str(list_item_id)
        print "checked: " + str(checked)
        print

        cnx = self.connect()
        cursor = cnx.cursor()

        set_checked_query = "UPDATE list_items SET checked = %s WHERE id = %s"
        cursor.execute(set_checked_query, (checked, list_item_id))
        update_changed_query = "UPDATE lists SET last_change = NULL WHERE id IN (SELECT list_id " \
                               "FROM list_items WHERE id = %s)"
        cursor.execute(update_changed_query, (list_item_id,))
        cnx.commit()
        cursor.close()
        cnx.close()

        print "Set {} to {}".format(list_item_id, checked)
        self.respond(True)

    #Handle requests of the following type:
    #host/create_account/name=hej&password=foo
    def create_account(self, request_params):
        name = request_params['name']
        password = request_params['password']

        print "name: " + name
        print "password: " + password
        print

        cnx = self.connect()
        cursor = cnx.cursor()
        create_account_query = ("INSERT INTO users (name, password)"
                                "VALUES (%s, %s)")
        cursor.execute(create_account_query, (name, password))
        cnx.commit()
        cursor.close()
        cnx.close()

        print "Inserted {}, {} into users".format(name, password)
        self.respond(True)

    #Handle requests of the following type:
    #host/get_lists/id=1
    def get_lists(self, request_params):
        #{"lists": {"id": 1, "title": "Shopping", "last_change": date,
        # "deadline": date, "items": [
        # {"id": 1, "content": "Buy eggs", "checked": false}, 
        # {"id": 2, "content": "Buy milk", "checked": true}]}}

        user_id = request_params['id']

        print "user id: " + user_id
        print

        cnx = self.connect()
        cursor = cnx.cursor()
        get_lists_query = ("SELECT id, title, author, last_change, deadline "
                           "FROM lists WHERE id IN (SELECT list_id FROM collaborators WHERE uid = %s)")
        cursor.execute(get_lists_query, user_id)

        lists = []

        #Get id, title, author, last change and deadline for every list
        for (list_id, title, author, last_change, deadline) in cursor:
            lists.append({"id": list_id, "title": title, "author": author,
                          "last_change": str(last_change), "deadline": str(deadline), "items": [], "collaborators": []})

        #Get all list items for each list
        get_list_items_query = "SELECT id, content, checked FROM list_items WHERE list_id = %s"
        for curr_list in lists:
            list_id = str(curr_list['id'])
            cursor.execute(get_list_items_query, (list_id,))
            for (item_id, content, checked) in cursor:
                curr_list['items'].append({"id": item_id, "content": content, "checked": checked == 1})

        get_collaborators_query = "SELECT id, name, date_created FROM users JOIN collaborators" \
                                  " ON users.id = collaborators.uid WHERE list_id = %s"
        #Get all collaborators for each list
        for curr_list in lists:
            list_id = str(curr_list['id'])
            cursor.execute(get_collaborators_query, (list_id,))
            for (user_id, name, date_created) in cursor:
                curr_list['collaborators'].append({"id": user_id, "name": name, "date_created": str(date_created)})

        #Print all the lists for the user
        for curr_list in lists:
            print curr_list['title'], str(curr_list['deadline'])
            for item in curr_list['items']:
                print item['content'], item['checked']
            print

        cursor.close()
        cnx.close()

        response = json.dumps({"lists": lists})
        self.respond(response)

    #Handle requests of the following type:
    #host/login/name=Dexter&password=bananpaj
    def login(self, request_params):
        name = request_params['name']
        password = request_params['password']
        print "name: " + name
        print "password: " + password
        print
        login_query = "SELECT id FROM users WHERE name = %s AND password = %s"
        cnx = self.connect()
        cursor = cnx.cursor()
        cursor.execute(login_query, (name, password))
        uid_tuple = cursor.fetchone()
        if uid_tuple is not None:
            print "Login successful"
            (uid,) = uid_tuple
            response = uid
        else:
            print "Login failed"
            response = False
        cursor.close()
        cnx.close()
        self.respond(response)

    #Handle requests of the following type:
    #host/remove_list_item/id=4
    def remove_list_item(self, request_params):
        list_item_id = request_params['id']
        print "id: " + list_item_id
        print

        remove_item_query = "DELETE FROM list_items WHERE id = %s"
        cnx = self.connect()
        cursor = cnx.cursor()
        cursor.execute(remove_item_query, (list_item_id,))
        cnx.commit()
        cursor.close()
        cnx.close()
        print "Removed list item " + list_item_id
        self.respond(True)

    #Handle requests of the following type:
    #host/share/name=qwe&list_id=4
    def share(self, request_params):
        username = request_params['name']
        list_id = request_params['list_id']
        print "Username: " + username
        print "List id: " + list_id
        print

        get_id_query = "SELECT id FROM users WHERE name = %s"
        share_query = "INSERT INTO collaborators (uid, list_id) VALUES (%s, %s)"
        cnx = self.connect()
        cursor = cnx.cursor()
        cursor.execute(get_id_query, (username,))
        uid_tuple = cursor.fetchone()
        if uid_tuple is not None:
            (uid,) = uid_tuple
            cursor.execute(share_query, (uid, list_id))
            cnx.commit()
            cursor.close()
            cnx.close()
            self.respond(uid)
            print "Inserted {}, {} into collaborators".format(username, list_id)
        else:
            cursor.close()
            cnx.close()
            self.respond(False)
            print "Share failed"

    #Connects to the database and returns the connection object
    def connect(self):
        cnx = mysql.connector.connect(user='dexteradmin', password='B3E2RaX5',
                                      host='mysql-vt2013.csc.kth.se',
                                      database='dexter')
        return cnx

    def respond(self, response):
        self.send_response(200)
        self.send_header('Content-Type', 'application/json')
        self.end_headers()
        self.wfile.write(response)


def run_server(server):
    server.serve_forever()


if __name__ == '__main__':
    print "----Starting server..."
    server_address = (HOST_NAME, PORT)
    server = HTTPServer(server_address, TodoRequestHandler)

    try:
        #Start HTTP server thread
        httpThread = threading.Thread(target=run_server, args=(server, ))
        httpThread.start()
        print "----HTTP Server started"

        # Keep alive
        while True:
            time.sleep(100)

    except (KeyboardInterrupt, SystemExit):
        print
        print "----Server has shut down"
        server.shutdown()
        sys.exit(0)
