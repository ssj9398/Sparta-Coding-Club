from flask import Flask, render_template
app = Flask(__name__)

@app.route('/')
def home():
   return render_template('index.html')

@app.route('/mypage')
def mypage():
   return 'mypage'

@app.route('/button')
def button():
   return '<button>버튼</button>'

if __name__ == '__main__':
   app.run('0.0.0.0',port=5000,debug=True)