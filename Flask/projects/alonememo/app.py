from flask import Flask, render_template, jsonify, request
app = Flask(__name__)

import requests
from bs4 import BeautifulSoup

from pymongo import MongoClient
client = MongoClient('localhost', 27017)
db = client.dbsparta

## HTML을 주는 부분
@app.route('/')
def home():
   return render_template('index.html')

@app.route('/memo', methods=['GET'])
def listing():
    memo = list(db.memo.find({}, {'_id': False}))
    return jsonify({'all_memos': memo})

## API 역할을 하는 부분
@app.route('/memo', methods=['POST'])
def saving():
    post_url = request.form['post_url']
    post_comment = request.form['post_comment']

    url = post_url

    headers = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36'}
    data = requests.get(url, headers=headers)

    soup = BeautifulSoup(data.text, 'html.parser')

    title = soup.select_one('meta[property="og:title"]')['content']
    image = soup.select_one('meta[property="og:image"]')['content']
    description = soup.select_one('meta[property="og:description"]')['content']

    doc = {
        'post_url': post_url,
        'post_comment': post_comment,
        'title' : title,
        'image' : image,
        'description' : description
    }
    db.memo.insert_one(doc)

    return jsonify({'msg':'POST 연결되었습니다!'})

if __name__ == '__main__':
   app.run('0.0.0.0',port=5000,debug=True)