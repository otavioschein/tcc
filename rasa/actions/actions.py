from typing import Any, Text, Dict, List
from rasa_sdk import Action, Tracker
from rasa_sdk.executor import CollectingDispatcher
from rasa_sdk.events import SlotSet
import requests
import spacy

class ActionListarNomesLivros(Action):
    def name(self) -> Text:
        return "action_listar_nomes_livros"
    
    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:
        
        response = requests.get("http://localhost:8080/assistant/search/books/names")
        data = response.json()

        dispatcher.utter_message(text=f"Nomes dos livros: {data}")

        return[]

class ActionBuscarLivroPorTitulo(Action):
    def name(self) -> Text:
        return "action_buscar_livro_por_titulo"
    
    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:

        titulo = tracker.get_slot("titulo_livro")

        responseMinhaBiblioteca = requests.get(f"http://localhost:8080/assistant/books/minha-biblioteca/titulo/{titulo}")
        responsePearson = requests.get(f"http://localhost:8080/assistant/books/pearson-biblioteca/titulo/{titulo}")

        dataMinhaBiblioteca = responseMinhaBiblioteca.json()
        dataPearson = responsePearson.json()

        message = f"Livros que encontrei para o título {titulo} informado: "
        messageMinhaBiblioteca = f"Na minha biblioteca: {dataMinhaBiblioteca}"
        messagePearson = f"e na biblioteca Pearson: {dataPearson}"

        dispatcher.utter_message(text=message)
        dispatcher.utter_message(text=messageMinhaBiblioteca)
        dispatcher.utter_message(text=messagePearson)

        return [SlotSet("titulo_livro", None)]
    
class ActionBuscarLivroPorAutor(Action):
    def name(self) -> Text:
        return "action_buscar_livro_por_autor"
    
    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:

        autor = tracker.get_slot("nome_autor")

        responseMinhaBiblioteca = requests.get(f"http://localhost:8080/assistant/books/minha-biblioteca/autor/{autor}")
        responsePearson = requests.get(f"http://localhost:8080/assistant/books/pearson-biblioteca/autor/{autor}")

        dataMinhaBiblioteca = responseMinhaBiblioteca.json()
        dataPearson = responsePearson.json()

        message = f"Aqui estão alguns livros que encontrei para o autor {autor}:"
        messageMinhaBiblioteca = f"Na minha biblioteca: {dataMinhaBiblioteca}"
        messagePearson = f"E na biblioteca pearson: {dataPearson}"

        dispatcher.utter_message(text=message)
        dispatcher.utter_message(text=messageMinhaBiblioteca)
        dispatcher.utter_message(text=messagePearson)
        
        return[SlotSet("nome_autor", None)]

class ActionExtrairNomeDeLivroNoInput(Action):
    def name(self) -> Text:
        return "action_extrair_nome_de_livro_no_input"
    
    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:
        
        nlp = spacy.load('pt_core_news_md')
        mensagem = tracker.latest_message.get('text', '')
        doc = nlp(mensagem)
        
        sentences = [ent.text for ent in doc.ents if ent.label_ == 'titulo_livro']

        if sentences:
            livro = sentences[0]
            return [SlotSet("titulo_livro", livro)]
        else:
            dispatcher.utter_message("Nenhum livro mencionado.")
        
class ActionExtrairNomeDeAutorNoInput(Action):
    def name(self) -> Text:
        return "action_extrair_nome_de_autor_no_input"
    
    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:
        
        nlp = spacy.load('pt_core_news_md')
        mensagem = tracker.latest_message.get('text', '')
        doc = nlp(mensagem)
        
        autor = [ent.text for ent in doc.ents if ent.label_ == 'PER']

        for a in autor:
            nome_autor = a
            dispatcher.utter_message(f"Autor mencionado: {a}.")
            return [SlotSet("nome_autor", nome_autor)]
        