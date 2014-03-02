from BaseHTTPServer import BaseHTTPRequestHandler, HTTPServer
import threading
import time
import sys
import MySQLdb

HOST_NAME = 'localhost' 
PORT = 8888


class TodoRequestHandler(BaseHTTPRequestHandler):
    def do_GET(self):
        request = self.path.split('/')
        userIP = self.address_string()
        print "Request from " + userIP + "..."

        for word in request:
            print word

        db = MySQLdb.connect(host="mysql-vt2013.csc.kth.se", user="dexteradmin", passwd="B3E2RaX5", db="dexter")

        cur = db.cursor()
        cur.execute("SELECT * FROM securities")

        self.send_response(200)
        self.send_header('Content-Type', 'application/json')
        self.end_headers()

        response = ""
        for row in cur.fetchall():
            response += row[1]
            response += " "
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
