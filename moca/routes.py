from app import app
from moca.models import Moca

@app.route('/api/v1/resources/topics/')
def get_topics():
    return Moca().get_topics()

@app.route('/api/v1/resources/events/all/')
def get_all_events():
    return Moca().get_all_events()

@app.route('/api/v1/resources/events/')
def get_events():
    return Moca().get_events()