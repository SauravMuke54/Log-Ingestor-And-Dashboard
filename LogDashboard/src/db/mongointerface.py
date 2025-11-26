import pandas as pd

from db.databaseinterface import DatabaseInterface
from utils.env import get_mongo_uri, get_database_name, get_collection_name
from pymongo import MongoClient

class MongoInterface(DatabaseInterface):

    def __init__(self):

        try :
            mongo_uri = get_mongo_uri()
            database_name = get_database_name()
            collection_name = get_collection_name()

            if mongo_uri is not None:
                self._client = MongoClient(mongo_uri)

            if database_name is not None:
                self._db = self._client[database_name]

            if self._db is not None and collection_name is not None:
                self._collection = self._db[collection_name]

        except Exception as e:
            print(e)

    def query_data(self, query_params: dict = None, sort_by: dict = None, limit: int = None):
        """
        Query data from the MongoDB collection.

        :param query_params: A dictionary of query filters. Example: {"level": "ERROR"}
        :param sort_by: A dictionary of sort parameters. Example: {"timestamp": -1}
        :param limit: Integer limit for the number of records to return.
        :return: A list of records (documents)
        """
        query_params = query_params or {}
        sort_by = sort_by or {}

        cursor = self._collection.find(query_params)

        if sort_by:
            cursor = cursor.sort(list(sort_by.items()))

        if limit and limit > 0:
            cursor = cursor.limit(limit)

        # Return as list
        return list(cursor)

    def get_all_logs(self):
        """
        :return: A list of all logs
        """
        return self.query_data()

    def get_logs(self, query_params: dict = None, sort_by: dict = None, limit: int = None):
        """
        :param query_params:  A dictionary of query filters. Example: {"level": "ERROR"}
        :param sort_by:  A dictionary of sort parameters. Example: {"timestamp": -1}
        :param limit: An integer limit for the number of records to return.
        :return: A list of records (documents)
        """
        return self.query_data(query_params, sort_by, limit)







