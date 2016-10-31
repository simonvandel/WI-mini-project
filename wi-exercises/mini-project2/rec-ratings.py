import pandas
from sklearn.neighbors import KNeighborsClassifier
import sklearn.preprocessing as preprocessing
import numpy as np
from sklearn.metrics import mean_absolute_error

def chunks(l, n):
    """Yield successive n-sized chunks from l."""
    for i in range(0, len(l), n):
        yield l[i:i + n]

data = []
with open("SentimentTrainingData.txt") as inFile:
        lines = inFile.read().split("\n")

        data_rows = [x for x in chunks(lines, 9)]

        for row in data_rows[:2000]:
            product_id = row[0].split()[1]
            raw_score = float(row[4].split()[1])
            #score = 0 if raw_score <= 3 else 1 
            #text = row[7].replace("<br/>", "").split()[1:]
            data.append((product_id, raw_score))

        print("done loading file, starting training")


dataframe = pandas.DataFrame(data)
le = preprocessing.LabelEncoder()
le.fit(dataframe[0])
dataframe = pandas.DataFrame(data[:1000])
dataframe[0] = le.transform(dataframe[0])

X = dataframe[[0]].values
Y = dataframe[[1]].values.ravel()

neigh = KNeighborsClassifier(n_neighbors=3)
neigh.fit(X, Y)
#product_ids = np.array(le.transform([[x[0]] for x in data[1001:1011]]))
prut = 100
start = 1201
hej = data[start:start+prut]
product_ids = le.transform([x[0] for x in hej])
print(product_ids)
product_ids = product_ids.reshape(-1,1)
ratings = [x[1] for x in hej]
res = neigh.predict(product_ids)
for x in zip(res, ratings):
    print(x)

print(mean_absolute_error(ratings, res))
#for x in product_ids:
#    print(neigh.predict(y))