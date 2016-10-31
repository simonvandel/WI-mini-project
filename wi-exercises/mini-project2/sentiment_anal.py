import collections
import math


class Model:
    def train_naive_bayes(self, data):
        # count number of reviews:  N
        N = len(data)

        print("lol")
        # count number of reviews with pos/neg: N(c)
        data_pos = list(filter(lambda x: x[1] == 1, data))
        data_neg = list(filter(lambda x: x[1] == 0, data))

        N_c_pos = float(len(data_pos))
        print(N_c_pos)
        N_c_neg = N - N_c_pos
        print(N_c_neg)

        print("haha")
        

        vocabulary = find_vocabulary(data)
        vocabulary_len = len(vocabulary)
        print("rofl")
        # count number of times word_i appears across pos/neg N(xi,c)
        
        # key is word, value is number of appearances
        N_xi_pos_dict = collections.Counter(get_all_words(data_pos))
        N_xi_neg_dict = collections.Counter(get_all_words(data_neg))
            
        print("yolo")

        # p(c) = N(c) / N
        p_pos = (N_c_pos + 1) / (N + 2)
        p_neg = (N_c_neg + 1) / (N + 2)

        def p_c(c):
            if c == 1:
                return p_pos
            else:
                return p_neg

        self.p_c = p_c

        # p(x | c) = N(xi, c) / N(c)
        def p_x_c(x,c):
            if c == 1:
                return (N_xi_pos_dict[x] + 1) / (N_c_pos + vocabulary_len)
            else:
                return (N_xi_neg_dict[x] + 1) / (N_c_neg + vocabulary_len)

        self.p_x_c = p_x_c

    def predict(self, input):
        # tokenize and handle not
        handled_input = handle_input(input)

        def calc_score(c):
            all_probabilities = map(lambda word: math.log(self.p_x_c(word, c)), handled_input)
            score = sum(all_probabilities) + math.log(self.p_c(c))
            return score

        # calc scores
        score_pos = calc_score(1)
        score_neg = calc_score(0)
        return 1 if score_pos >= score_neg else 0
        #return (score_pos, score_neg)

def tokenize(string):
    return string.split()

def handle_not(words):
    res = []
    not_encountered = False
    for word in words:
        if not_encountered:
            res.append(word + "_neg")
        else:
            res.append(word)
        if "." in word or "," in word:
            not_encountered = False
        if word == "not":
            not_encountered = True
    return res
        
def handle_input(input):
    return handle_not(tokenize(input))

def flatten(lst):
    return [item for sublist in lst for item in sublist]

def get_all_words(data):
    x = map(lambda x: x[0], data)
    return flatten(x)

def find_vocabulary(data):
    # find the vocabulary
    all_words = get_all_words(data)
    print("roflcopter")
    all_words_inc_neg = []
    xs = filter(lambda w: "_neg" not in w, all_words)
    print("asdasd")
    for word in xs:
        all_words_inc_neg.append(word)
        all_words_inc_neg.append(word + "_neg")
    
    print("lmao")
    return collections.Counter(all_words_inc_neg).keys()

# with open("exTrainingData.txt") as inFile:
#     lines = inFile.read().split("\n")

#     data = []
#     for line in lines:
#         words = handle_input(line[:-1])

#         label = int(line[-1])
#         data.append((words, label))

#     model = Model()

#     model.train_naive_bayes(data)
