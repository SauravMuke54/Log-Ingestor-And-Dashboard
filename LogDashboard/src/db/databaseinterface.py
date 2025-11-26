from abc import ABC, abstractmethod

class DatabaseInterface(ABC):

    @abstractmethod
    def query_data(self):
        raise NotImplementedError()

