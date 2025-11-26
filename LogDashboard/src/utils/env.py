from dotenv import load_dotenv
import os
load_dotenv()

def get_env_variable(var_name):
    try:
        return os.environ[var_name]
    except KeyError:
        raise Exception(f"{var_name} not set")

def get_mongo_uri():
    return get_env_variable("MONGO_URI")

def get_database_name():
    return get_env_variable("DATABASE_NAME")

def get_collection_name():
    return get_env_variable("COLLECTION_NAME")

