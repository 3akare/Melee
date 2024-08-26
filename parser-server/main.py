from flask import Flask, request, jsonify

app = Flask(__name__)

@app.route('/process', methods=['POST'])
def process_text():
    payload = request.get_data().decode()
    return str(payload)

if __name__ == "__main__":
    app.run(host="0.0.0.0", port="5500", debug=True)