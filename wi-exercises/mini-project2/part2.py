from sentiment_anal import Model
import collections
import sys

def chunks(l, n):
    """Yield successive n-sized chunks from l."""
    for i in range(0, len(l), n):
        yield l[i:i + n]

def trainModel():
    model = Model()
    with open("SentimentTrainingData.txt") as inFile:
        lines = inFile.read().split("\n")

        data_rows = [x for x in chunks(lines, 9)]

        data = []
        for row in data_rows:
            raw_score = float(row[4].split()[1])
            score = 0 if raw_score <= 3 else 1 
            text = row[7].replace("<br/>", "").split()[1:]
            data.append((text, score))

        print(collections.Counter(map(lambda x: x[1],data)))

        print("done loading file, starting training")
        model.train_naive_bayes(data)
        return model

# while True:
#     hej = input("")
#     print(model.predict(hej))
