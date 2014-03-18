from BaseHTTPServer import BaseHTTPRequestHandler, HTTPServer
from datetime import date, datetime
import threading
import time
import sys
import mysql.connector
import json
import urllib

HOST_NAME = 'localhost'
PORT = 8888


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
            #Prepare key and value for use in database query here

        #Execute the method that corresponds to the request type
        if request_type == "test":
            self.test_action(request_params)
        elif request_type == "new_list":
            self.new_list(request_params)
        elif request_type == "new_list_item":
            self.new_list_item(request_params)
        elif request_type == "set_checked_status":
            self.set_checked_status(request_params)
        elif request_type == "create_account":
            self.create_account(request_params)
        elif request_type == "get_lists":
            self.get_lists(request_params)

    def test_action(self, request_params):
        test_string = request_params['foo']
        test_string2 = request_params['bar']
        print test_string
        print test_string2
        response = json.dumps([{"checked": False, "content": "eggs"}, {"checked": True, "content": "wat"}])
        print response
        self.respond(response)

    #Handle requests of the following type:
    #host/new_list/title=Shopping&author=1&deadline=1395140712
    def new_list(self, request_params):
        title = request_params['title']
        author = request_params['author']
        last_change = datetime.now()
        if request_params['deadline'] == "":
            deadline = None
        else:
            deadline = float(request_params['deadline'])
            #TODO: handle this correclty
            deadline = datetime.fromtimestamp(deadline / 1000.0)

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
        cnx.commit()
        cursor.close()
        cnx.close()
        print "Inserted {}, {}, {}, {} into lists.".format(title, author, last_change, deadline)

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
        cnx.commit()
        cursor.close()
        cnx.close()
        print "Inserted {}, {}, {} into list_items".format(list_id, content, False)

    #Handle requests of the following type:
    #set_checked_status/id=1&checked=false
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
        cnx.commit()
        cursor.close()
        cnx.close()

        print "Set {} to {}".format(list_item_id, checked)

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
        for (list_id, title, author, last_change, deadline) in cursor:
            lists.append({"id": list_id, "title": title, "author": author,
                          "last_change": str(last_change), "deadline": str(deadline), "items": []})

        get_list_items_query = "SELECT id, content, checked FROM list_items WHERE list_id = %s"
        for curr_list in lists:
            list_id = str(curr_list['id'])
            cursor.execute(get_list_items_query, list_id)
            for (item_id, content, checked) in cursor:
                curr_list['items'].append({"id": item_id, "content": content, "checked": checked == 1})

        for curr_list in lists:
            print curr_list['title'], str(curr_list['deadline'])
            for item in curr_list['items']:
                print item['content'], item['checked']
            print

        cursor.close()
        cnx.close()

        response = json.dumps({"lists": lists})
        self.respond(response)

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
