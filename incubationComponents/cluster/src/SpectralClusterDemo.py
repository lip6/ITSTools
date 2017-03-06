from operator import concat
print(__doc__)

# Author: Kemal Eren <kemal@kemaleren.com>
# License: BSD 3 clause

# We only need to import this module
import os.path

import numpy as np
from matplotlib import pyplot as plt


from sklearn.datasets import make_biclusters
from sklearn.datasets import samples_generator as sg
from sklearn.cluster.bicluster import SpectralCoclustering
from sklearn.cluster.bicluster import SpectralBiclustering
from sklearn.metrics import consensus_score

# data, rows, columns = make_biclusters(
#      shape=(300, 300), n_clusters=5, noise=5,
#      shuffle=False, random_state=0)

# for i in range(len(data)):
#     print data[i]
# 
# print rows
# print columns


def plot (fname):
    f = open(fname)
    s= f.readlines()
    f.close()

    data = []

    for i in range(1, len(s)):
        line = s[i]
        if (not line.strip('\n')) :
            continue
        t= line.split('\t')
        tcopy = []
        nonzeros = 0 
        for j in range(1, len(t)):
            fl = float (t[j])
            tcopy.append(fl)
            if (fl != 0) :
                nonzeros = 1
        if (len(tcopy) >= 1 and nonzeros==1):
            data.append( tcopy )
        else :
            print 'zero line :' +i
    print "last line :" + s[len(s)-1]
    print "last data :" + str(len(data[-1]))
    
        
    for i in range(len(data)):
        for j in range(len(data[i])):
            if (data[i][j] != 0 and data[i][j] != 1) :
                print "i="+i + " j=" + j + " data=" + data[i][j]
# print rows
# print columns

#rows = range(len(data))
#columns = range(len(data[0]))

# exit()
    zeros = []
    for j in range(len(data[0])):
        nonzeros = 0 
        for i in range(len(data)):
            if (data[i][j]!=0):
                nonzeros =1
                break
        if (nonzeros == 0) :
            zeros.append(j)
            print "killing column "+ str(j)           
    
    
    for j in range(len(zeros)-1,-1,-1):
        for i in range(len(data)):
            del data[i][zeros[j]]
        print "killed column "+ str(zeros[j])  
        
    adata = np.array(data)


    fname = fname.split('/')[-2]
    
    try :
        nbclusters = int (fname.split('-')[-1]) * 2
        if (nbclusters < len(data)/5  or nbclusters >= len(data)/2 ) :
            nbclusters = len(data)/3   
    except  :
        # len(data)/5
        nbclusters = len(data)/3  
 
    fig = plt.figure(figsize=(8, 4))
    fig.subplots_adjust(left=0.02, right=0.98, bottom=0.05, top=0.9)   

    ax = fig.add_subplot(1,2,1)
    ax.set_title("Original "+fname)
    ax.matshow(adata, cmap=plt.cm.Blues)  # @UndefinedVariable
#     plt.matshow(adata, cmap=plt.cm.Blues)
#     plt.title("Original "+fname)
    
#     adata, row_idx, col_idx = sg._shuffle(adata, random_state=0)
#     ax = fig.add_subplot()
#     ax.set_title("Shuffled dataset")
#     ax.matshow(data, cmap=plt.cm.Blues)

 
    model = SpectralCoclustering(n_clusters=nbclusters, random_state=0)

    bdata = np.array(adata)
    #for i in range(0,10):
    model.fit(bdata)
 
    # score = consensus_score(model.biclusters_,
    #                         (rows[:, row_idx], columns[:, col_idx]))
    #   
    # print "consensus score: {:.3f}".format(score)
 
 
    li = 0
    for line in bdata :
        lj = 0
        for val in line :
            if (val != 0):
                bdata[li][lj] = model.row_labels_[li] + 1
            lj += 1
        li+=1
               
    fit_data = bdata[np.argsort(model.row_labels_)]
    fit_data = fit_data[:, np.argsort(model.column_labels_)]
    
    
    ax = fig.add_subplot(1,2,2)
    ax.set_title("Coclustered ("+str(nbclusters) + ") "+ fname)
    ax.matshow(fit_data, cmap=plt.cm.Blues)  # @UndefinedVariable
    
    
    # BI CLUSTER CODE
#     model = SpectralBiclustering(n_clusters=nbclusters, random_state=0)
# 
#     model.fit(adata)
#                
#     fit_data = adata[np.argsort(model.row_labels_)]
#     fit_data = fit_data[:, np.argsort(model.column_labels_)]
#       
#     ax = fig.add_subplot(1,3,2)
#     ax.set_title("Biclustered ("+str(nbclusters) + ") "+ fname)
#     ax.matshow(fit_data, cmap=plt.cm.Blues)  # @UndefinedVariable
    
    
        


topdir = "/data/thierry/SUMO/2014/workspace/MCC2014/"

def step(exten, dirname, names):
    
    for name in names:
        if name == "model.txt":
            print(os.path.join(dirname, name))
            plot(os.path.join(dirname, name))
 
# Start the walk
os.path.walk(topdir, step, "hi")

plt.show()
