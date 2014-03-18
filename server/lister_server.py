from BaseHTTPServer import BaseHTTPRequestHandler, HTTPServer
from datetime import date, datetime, timedelta
import threading
import time
import sys
import mysql.connector

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


        #Find all parameters by splitting the third argument in URL on & character
        #host/type/param1=foo&param2=bar
        #Put all keys and values in a python dictionary for easy access
        request_params_strings = request[2].split("&")
        request_params = {}
        for param in request_params_strings:
            key_and_value = param.split("=")
            key = key_and_value[0]
            value = key_and_value[1]
            request_params[key] = value
            #Prepare key and value for use in database query here

        if request_type == "test":
            self.test_action(request_params)
        elif request_type == "new_list":
            self.new_list(request_params)

    def test_action(self, request_params):
        test_string = request_params['foo']
        test_string2 = request_params['bar']
        print test_string
        print test_string2
        self.respond(test_string)

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
            deadline = datetime.fromtimestamp(deadline/1000.0)

        cnx = self.connect()
        cursor = cnx.cursor()
        new_list_query = ("INSERT INTO lists (title, author, last_change, deadline)"
            "VALUES (%s, %s, %s, %s)")

        cursor.execute(new_list_query, (title, author, last_change, deadline))
        cnx.commit()
        cursor.close()
        cnx.close()

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
