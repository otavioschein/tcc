from train_data_model_md_222 import TRAIN_DATA
import spacy
from spacy.util import minibatch, compounding
import random
from spacy.training import Example

nlp = spacy.load("./tunned-model-md-v1")
ner = nlp.get_pipe("ner")

if "BOOK" not in ner.labels:
    ner.add_label("BOOK")

other_pipes = [pipe for pipe in nlp.pipe_names if pipe != "ner"]
with nlp.disable_pipes(*other_pipes):
    optimizer = nlp.resume_training()
    for epoch in range(12):
        random.shuffle(TRAIN_DATA)
        losses = {}

        for text, annotations in TRAIN_DATA:
            doc = nlp.make_doc(text)
            example = Example.from_dict(doc, annotations)
            nlp.update([example], drop=0.5, losses=losses)
        print(f"Iteration {epoch + 1}, Losses {losses}")

output_dir = "./tunned-model-md-v2"
nlp.to_disk(output_dir)
print("Processo finalizado.")