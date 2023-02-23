from sqlalchemy import create_engine
from sqlalchemy.orm import scoped_session, sessionmaker
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy import Column, Integer, String

USER = "postgres"
PW = "simon980705"
URL = "map-database.cyhsx6deaq0u.ap-northeast-2.rds.amazonaws.com"
PORT = "5432"
DB = "postgres"
engine = create_engine("postgresql://{}:{}@{}:{}/{}".format(USER, PW, URL,PORT, DB))
db_session = scoped_session(sessionmaker(autocommit=False, autoflush=False, bind=engine))

Base = declarative_base()
Base.query = db_session.query_property()


class User(Base):
    __tablename__ = 'users2'
    id = Column(Integer, primary_key=True)
    name = Column(String(50), unique=True)
    passwd = Column(String(120), unique=False)
    map_1 = Column(Integer, unique=False)
    map_2 = Column(Integer, unique=False)

    def __init__(self, name=None, passwd=None, map_1=None, map_2=None):
        self.name = name
        self.passwd = passwd
        self.map_1 = map_1
        self.map_2 = map_2

    def __repr__(self):
        return f'<User{self.name!r}>'


Base.metadata.create_all(bind=engine)

from flask import Flask
from flask import request
from flask import jsonify
from werkzeug.serving import WSGIRequestHandler
import json

WSGIRequestHandler.protocol_version = "HTTP/1.1"
app = Flask(__name__)


@app.route("/adduser", methods=['POST'])
def add_user():
    content = request.get_json(silent=True)
    name = content["name"]
    passwd = content["passwd"]
    if db_session.query(User).filter_by(name=name).first() is None:
        u = User(name=name, passwd=passwd, map_1=0, map_2=0)
        db_session.add(u)
        db_session.commit()
        return jsonify(success=True)
    else:
        return jsonify(success=False)


@app.route("/login", methods=['POST'])
def login():
    content = request.get_json(silent=True)
    name = content["name"]
    passwd = content["passwd"]
    check = False
    result = db_session.query(User).all()
    for i in result:
        if i.name == name and i.passwd == passwd: check = True
    return jsonify(success=check)


@app.route("/map1", methods=['POST'])
def map1():
    content = request.get_json(silent=True)
    name = content["name"]
    map_1 = content["map_1"]
    check = False
    result = db_session.query(User).all()
    for i in result:
        if i.name == name:
            if i.map_1 < map_1:
                i.map_1 = map_1
            check = True
    db_session.commit()
    return jsonify(success=check)


@app.route("/map2", methods=['POST'])
def map2():
    content = request.get_json(silent=True)
    name = content["name"]
    map_2 = content["map_2"]
    check = False
    result = db_session.query(User).all()
    for i in result:
        if i.name == name:
            if i.map_2 < map_2:
                i.map_2 = map_2
            check = True
    db_session.commit()
    return jsonify(success=check)


@app.route("/get/map1", methods=['GET'])
def get_map1():
    ret = {}
    result = db_session.query(User).order_by(User.map_1.desc())
    count = 0
    ret["rank1_name"] = "none"
    ret["rank1_map1"] = 0
    ret["rank2_name"] = "none"
    ret["rank2_map1"] = 0
    ret["rank3_name"] = "none"
    ret["rank3_map1"] = 0

    for i in result:
        if count == 0:
            ret["rank1_name"] = i.name
            ret["rank1_map1"] = i.map_1
        if count == 1:
            ret["rank2_name"] = i.name
            ret["rank2_map1"] = i.map_1
        if count == 2:
            ret["rank3_name"] = i.name
            ret["rank3_map1"] = i.map_1
        count = count + 1

    return jsonify(ret)



@app.route("/get/map2", methods=['GET'])
def get_map2():
    ret = {}
    result = db_session.query(User).order_by(User.map_2.desc())
    count = 0
    ret["rank1_name"] = "none"
    ret["rank1_map2"] = 0
    ret["rank2_name"] = "none"
    ret["rank2_map2"] = 0
    ret["rank3_name"] = "none"
    ret["rank3_map2"] = 0

    for i in result:
        if count == 0:
            ret["rank1_name"] = i.name
            ret["rank1_map2"] = i.map_2
        if count == 1:
            ret["rank2_name"] = i.name
            ret["rank2_map2"] = i.map_2
        if count == 2:
            ret["rank3_name"] = i.name
            ret["rank3_map2"] = i.map_2
        count = count + 1

    return jsonify(ret)





if __name__ == "__main__":
    app.run(host='localhost', port=8888)
