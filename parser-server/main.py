from flask import Flask, request, jsonify
from pymongo import MongoClient
from flask_cors import CORS

from langchain_ollama import OllamaLLM

llm = OllamaLLM(model="llama3.1")

app = Flask(__name__)
CORS(app, resources={r"/api/*": {"origins": "*"}})
client = MongoClient('localhost', 27017)
db = client.MeleeDatabase
data_collection = db.parse_data



@app.route('/api/process', methods=['POST'])
def process_text():
    data = request.json
    text = data.get("data", "")

    print(text)

    prompt ="""
    Please provide the following information for each article related to the specified topics, formatted as JSON: the publisher's name, the direct and valid URL to the article, the exact title of the article, the author's name, and the publication date. Ensure that the URL is correct and leads to the actual article without errors. Do not fabricate or guess any details—only include accurate and verifiable information as if you were a professional data analyst or editor. The JSON should look like this:json
    {
        "publisher": "Name of Publisher",
        "url": "URL to the actual article",
        "title": "Title of the post",
        "author": "Author of the article",
        "date": "Date of publication"
    }
    """ + text

    print(prompt)
    response = llm.invoke(prompt)
    print(type(response))
    return "jsonify()"

@app.route("/api/blogs", methods=['GET'])
def blog_posts():
    data = list(data_collection.find({}, {}))  # Corrected projection syntax
    return jsonify(data)

if __name__ == "__main__":
    app.run(host="0.0.0.0", port="5500", debug=True)
