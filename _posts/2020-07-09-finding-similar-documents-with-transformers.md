---
layout: post
title:  "Finding similar documents with transformers"
date:   2020-07-09 07:30:00 +0000
---

*(Originally published in the [Codegram blog](https://www.codegram.com/blog/finding-similar-documents-with-transformers/){:target="_blank"})*

Whenever you have large amounts of text documents that you need to search, full-text search is a tried and true approach that has been popular for a long time. Specialized search engines such as ElasticSearch and even mainstream databases such as PostgreSQL support it.

However, whenever language in those documents is highly context-dependent, full-text search falls apart. Human language is rich —words in different contexts have different meanings, and users can often find themselves sifting through meaningless results to find that one document they're looking for.

## Why not just stick with full-text search?

Our goal is to be able to index a large number of documents and issue simple text queries similarly to a full-text search engine like ElasticSearch, but have them be context- and semantically aware.

A big advantage for users of this search engine is that they won't have to sift through irrelevant documents just because of structural similarity (different documents containing similar words, even if used in different contexts).

Perhaps more importantly, such a search engine has the capability to surface documents that are apparently different (they use different words altogether), but semantically very close —which would be clearly apparent to a human reading them, but not to a full-text search engine.

## How can we understand context rather than just matching words?

Recent advances in Natural Language Processing, namely the **Transformer**, have changed the scenery for good. Transformers are a family of neural networks that have seen great success in language modeling tasks (modeling statistical relationships between words in natural language).

Recently, a type of transformer by Google Research called [BERT](https://github.com/google-research/bert) has revolutionized the field of NLP. Since its advent, this type of neural network has become a bountiful area of research on its own.

By using transformers to process documents, rather than just matching words like full-text search does, we can turn a very opaque representation (text) into compact, abstract representations, much easier to deal with programmatically.

## Representing documents as vectors

You can think of a transformer narrowly as a function from a piece of text to a vector, or array of numbers. Importantly, we want this vector **to always have a constant length**, so that we can easily compare it to other vectors extracted from other documents.

A bit more philosophically, you can think of each number in the vector as a **coordinate in an N-dimensional space** (where N is the length of the vector). The working assumption here is that, if the transformer learned a useful representation of the document, **similar documents will live close together in that N-dimensional space**.

## Finding similar documents

So, now we have a bunch of vectors extracted from our documents. If they depict an N-dimensional space where similar documents live close to each other, we just need to find the nearest documents in that space, that is, where the coordinates for each dimension are closest to each other.

ElasticSearch happens to support a `dense_vector` type, which can index such vectors, and ranking results according to the [Euclidean distance](https://en.wikipedia.org/wiki/Euclidean_distance) to a specific point in that N-dimensional space. Very convenient, however, for the purposes of this demonstration, we'll use an in-memory database that can index dense vectors as well.

## Let's try it out!

How about we try to hack a prototype in 5 minutes? We can use [HuggingFace](http://huggingface.co)'s `transformers` library for the highest convenience, and as mentioned, instead of ElasticSearch we'll use an in-memory vector search library called `faiss`.

```bash
brew install libomp # if you are on OSX, for faiss
pip install transformers faiss torch
```

For our purposes we'll use a DistilBERT model in English (think of DistilBERT as a compressed BERT that runs faster), but there are tons of other models available in the `transformers` library, also for other languages and use cases (including our very own [Calbert](http://github.com/codegram/calbert), for Catalan!).

```python
from transformers import AutoModel, AutoTokenizer

tokenizer = AutoTokenizer.from_pretrained("distilbert-base-uncased")
model = AutoModel.from_pretrained("distilbert-base-uncased")

documents = [
    "That restaurant was not as good as the last movie I watched.",
    "I'm selling a used car in good condition",
    "Food was okay, the rest so so",
    "I love cats, but don't really like hyenas",
    "On the road, you must be careful",
]

vectors = [
  # tokenize the document, return it as PyTorch tensors (vectors),
  # and pass it onto the model
  model(**tokenizer(document, return_tensors='pt'))[0].detach().squeeze()
  for document in documents
]

[v.size() for v in vectors]
# => [torch.Size([15, 768]),
#     torch.Size([12, 768]),
#     torch.Size([10, 768]),
#     torch.Size([15, 768]),
#     torch.Size([10, 768])]
```

We encoded the documents into vectors, but we see a little issue —these encodings are vectors of size `15x768`, `12x768`, `10x768`, `15x768` and `10x768` respectively. Since they have different sizes, they cannot be compared. Why is that?

These vectors are not yet the final representation we want for our documents —they are 768-dimensional vectors **for each token in the document**. Since documents have a different number of tokens (because some texts are longer than others), we end up with this.

Rather than the coordinates of **each token** in the 768-dimensional space, we want to find the **general coordinates** of the document, which we can do by averaging the points and finding the center. (There are more sophisticated ways, but this will do for now.)

```python
import torch

averaged_vectors = [torch.mean(vector, dim=0) for vector in vectors]

[v.size() for v in averaged_vectors]
# => [torch.Size([768]), torch.Size([768]), torch.Size([768]), torch.Size([768]), torch.Size([768])]
```

We got it! A unified, compact representation for each of our documents, all of size 768. Let's pack it in a function we can reuse:

```python
def encode(document: str) -> torch.Tensor:
  tokens = tokenizer(document, return_tensors='pt')
  vector = model(**tokens)[0].detach().squeeze()
  return torch.mean(vector, dim=0)
```

Now let's index those documents in an in-memory vector-space database, to test:

```python
import faiss
import numpy as np

index = faiss.IndexIDMap(faiss.IndexFlatIP(768)) # the size of our vector space
# index all the documents, we need them as numpy arrays first
index.add_with_ids(
    np.array([t.numpy() for t in averaged_vectors]),
    # the IDs will be 0 to len(documents)
    np.array(range(0, len(documents)))
)

def search(query: str, k=1):
  encoded_query = encode(query).unsqueeze(dim=0).numpy()
  top_k = index.search(encoded_query, k)
  scores = top_k[0][0]
  results = [documents[_id] for _id in top_k[1][0]]
  return list(zip(results, scores))
```

Now we can try to search documents similar to a specific one:

```python
documents[1]
# => "I'm selling a used car in good condition"

search(documents[1], k=2)
# => [("I'm selling a used car in good condition", 70.69184),
#     ('On the road, you must be careful', 53.795788)]
```

Queries don't need to be documents, since we can encode queries into the vector space and treat them as documents to find similar ones to:

```python
search("I know how to drive", k=2)
# => [('On the road, you must be careful', 54.49833),
#     ("I'm selling a used car in good condition", 53.58915)]
```

## Conclusion

In this blogpost we've learned how transformers, the current state of the art in Natural Language Processing, can help us distill text documents into points in N-dimensional vector spaces.

By searching by distance to points in that space, we can discover documents similar to each other, as well as similar to user-crafted queries, creating a semantic search engine in a few lines of Python.

As a next step, I encourage you to try different models and languages (you can find them all in HuggingFace's [Model Hub](https://huggingface.co/models)) and also, try using ElasticSearch `dense_vector`s to index your documents there and take advantage of multi-faceted, production-ready search in vector space.
