from flask import Flask, request, jsonify
from pymongo import MongoClient
from flask_cors import CORS
import json

app = Flask(__name__)
CORS(app, resources={r"/api/*": {"origins": "*"}})
client = MongoClient('localhost', 27017)

db = client.MeleeDatabase
data_collection = db.parse_data

@app.route('/api/process', methods=['POST'])
def process_text():
    payload = request.get_data().decode()
    return str(payload)

@app.route("/api/blogs", methods=['GET'])
def blog_posts():
    data = list(data_collection.find({}, {}))  # Corrected projection syntax
    return jsonify(data)

if __name__ == "__main__":
    app.run(host="0.0.0.0", port="5500", debug=True)
