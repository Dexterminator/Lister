from BaseHTTPServer import BaseHTTPRequestHandler, HTTPServer
import threading
import time
import sys

HOST_NAME = 'localhost' 
PORT = 8888


class TodoRequestHandler(BaseHTTPRequestHandler):
    def do_GET(self):
        request = self.path.split('/')
        userIP = self.address_string()

        for word in request:
            print word

        self.send_response(200)
        self.send_header('Content-Type', 'application/json')
        self.end_headers()
        self.wfile.write("Hello server world!")


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
