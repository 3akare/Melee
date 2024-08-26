from flask import Flask, request, jsonify
from transformers import LongformerTokenizer, LongformerForSequenceClassification
import torch

app = Flask(__name__)

tokenizer = LongformerTokenizer.from_pretrained('allenai/longformer-base-4096')
model = LongformerForSequenceClassification.from_pretrained('allenai/longformer-base-4096')

@app.route('/process', methods=['POST'])
def process_text():
    return jsonify(request)

if __name__ == "__main__":
    app.run(host="0.0.0.0", port="5500")