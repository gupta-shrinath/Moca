from app import app
from meetup.models import Meetup

@app.route('/meetup/kotlin/')
def get_kotlin_events():
    return Meetup().get_meetups('kotlin-mumbai')