from flask import Flask, Response
import json
import datetime
import time
import random
import threading
from flask import request

app = Flask(__name__)

port_number = 0

@app.route('/sse')
def sse():
    value = request.args.get('value')
    def generate():
        while True:
            time.sleep(1)
            json_data = json.dumps({str(random.choice([1, 2, 3])): value})
            yield 'data: {}\n\n'.format(json_data)
    return Response(generate(), mimetype='text/event-stream')

def run_app(port):
    app.run(port=port)

if __name__ == '__main__':
    thread1 = threading.Thread(target=run_app, args=(5002,))
    thread2 = threading.Thread(target=run_app, args=(5003,))
    thread1.start()
    thread2.start()
    thread1.join()
    thread2.join()