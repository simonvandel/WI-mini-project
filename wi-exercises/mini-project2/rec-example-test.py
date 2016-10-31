import pandas
from sklearn.neighbors import KNeighborsClassifier
import sklearn.preprocessing as preprocessing

dataframe = pandas.read_csv("rec-examples.csv", delim_whitespace=True)

le = preprocessing.LabelEncoder()
le.fit(dataframe["hairColor"])
dataframe["hairColor"] = le.transform(dataframe["hairColor"])

X = dataframe[["height", "hairColor"]].values

Y = dataframe[["hotness"]].values.ravel()

neigh = KNeighborsClassifier(n_neighbors=1)
neigh.fit(X, Y)
lk = le.transform(["brown"])[0]
print(neigh.predict(["209", lk]))