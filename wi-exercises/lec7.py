import numpy as np
from numpy.linalg import eig
import networkx as nx

G = nx.Graph()
G.add_node(1)
G.add_node(2)
G.add_node(3)
G.add_node(4)
G.add_node(5)
G.add_node(6)
G.add_edge(1,2)
G.add_edge(2,3)
G.add_edge(1,3)
G.add_edge(3,4)
G.add_edge(4,5)
G.add_edge(4,6)
G.add_edge(5,6)

L = nx.laplacian_matrix(G)
L_mat = L.todense()

L_dec = eig(L_mat)
print(L_dec)
