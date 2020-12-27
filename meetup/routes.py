from app import app
from meetup.models import Meetup

@app.route('/meetup/kotlin/')
def get_kotlin_events():
    return Meetup().get_meetups('kotlin-mumbai',100001)

@app.route('/meetup/javascript/')
def get_javascript_events():
    return Meetup().get_paramter_meetups('build-with-code-los-angeles','JavaScript',100002)

