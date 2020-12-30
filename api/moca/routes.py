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

@app.route('/api/v1/resources/add_event/')
def get_moca_event():
    return Moca().add_event()
