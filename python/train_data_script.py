import csv
import random
from initial_phrase import INITIAL_PHRASE
from end_phrase import END_PHRASE

with open('../../../dataset-livros.csv', 'r', encoding='utf-8') as dataset_csv:
    csv_reader = csv.DictReader(dataset_csv)
    titulos = [row['titulo'] for row in csv_reader]

TRAIN_DATA = []

for titulo in titulos:
    random_index_initial = random.randint(0, 144)
    random_index_end = random.randint(0, 144)
    frase = INITIAL_PHRASE[random_index_initial] + titulo + END_PHRASE[random_index_end]
    start_index = frase.find(titulo)
    end_index = start_index + len(titulo)

    TRAIN_DATA.append((frase, {"entities": [(start_index, end_index, "BOOK")]}))

output_filename = 'train_data_model_2.py'

with open(output_filename, 'w', encoding='utf-8') as output_file:
    output_file.write("TRAIN_DATA = [\n")
    for data in TRAIN_DATA:
        frase, entities = data
        output_file.write(f'    ("{frase}", {{"entities": {entities["entities"]}}}), \n')
    output_file.write("]\n")

print(f'O arquivo {output_filename} foi criado com sucesso.')
