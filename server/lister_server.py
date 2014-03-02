from BaseHTTPServer import BaseHTTPRequestHandler, HTTPServer
import threading
import time
import sys
import MySQLdb

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
        request_params = request[2].split("&")
        for param in request_params:
            key_and_value = param.split("=")
            key = key_and_value[0]
            value = key_and_value[1]
            #Prepare key and value for use in database query here
            print "Key: " + key
            print "Value: " + value
            print

        db = MySQLdb.connect(host="mysql-vt2013.csc.kth.se", user="dexteradmin", passwd="B3E2RaX5", db="dexter")
        cur = db.cursor()
        cur.execute("SELECT * FROM securities WHERE name=%s", "Telia")

        self.send_response(200)
        self.send_header('Content-Type', 'application/json')
        self.end_headers()

        response = ""
        for row in cur.fetchall():
            print row[1]
            response += row[1]
            response += " "
        #Print the response
        cur.close()
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
