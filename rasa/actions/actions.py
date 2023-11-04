from typing import Any, Text, Dict, List
from rasa_sdk import Action, Tracker
from rasa_sdk.executor import CollectingDispatcher
from rasa_sdk.events import SlotSet, SessionStarted, ActionExecuted, EventType
import requests
import spacy
import uuid

class LivroObj:
    def __init__(self, id, autor, titulo, acesso):
        self.id = id
        self.autor = autor
        self.titulo = titulo
        self.acesso = acesso


class ActionSessionStart(Action):
    def name(self) -> Text:
        return "action_session_start"

    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[EventType]:

        print("ACTION SESSION START")

        events = [SessionStarted()]

        session_id = str(uuid.uuid1())
        events.append(SlotSet("session_id", session_id))

        mensagem = "Olá, bem-vindo ao BookWise! Você pode pesquisar livros por título e por autor, o que você gostaria?"
        mensagem2 = "Tente escrever o nome do livro ou nome do autor da forma mais correta possível para eu trazer o melhor resultado!"
        dispatcher.utter_message(mensagem)
        dispatcher.utter_message(mensagem2)

        return events


class ActionBuscarLivroPorTitulo(Action):
    def name(self) -> Text:
        return "action_buscar_livro_por_titulo"
    
    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:

        dispatcher.utter_message("Buscando livros...")

        titulo = tracker.get_slot("titulo_livro")
        session_id = tracker.get_slot("session_id")

        responseMinhaBiblioteca = requests.get(f"http://localhost:8080/assistant/{session_id}/books/minha-biblioteca/titulo/{titulo}")
        responsePearson = requests.get(f"http://localhost:8080/assistant/{session_id}/books/pearson-biblioteca/titulo/{titulo}")
        responseFisica = requests.get(f"http://localhost:8080/assistant/{session_id}/books/biblioteca-fisica/titulo/{titulo}")

        dataMinhaBiblioteca = responseMinhaBiblioteca.json()
        livrosMinhaBiblioteca = []

        dataPearson = responsePearson.json()
        livrosPearson = []

        dataFisica = responseFisica.json()
        livrosFisica = []

        for livro in dataMinhaBiblioteca["livroList"]:
            livroObj = LivroObj(
                id=livro['id'],
                autor=livro['autor'],
                titulo=livro['titulo'],
                acesso=livro['acesso']
            )
            livrosMinhaBiblioteca.append(livroObj)

        for livro in dataPearson["livroList"]:
            livroObj = LivroObj(
                id=livro['id'],
                autor=livro['autor'],
                titulo=livro['titulo'],
                acesso=livro['acesso']
            )
            livrosPearson.append(livroObj)

        for livro in dataFisica["livroList"]:
            fisicaObj = LivroObj(
                id=livro['id'],
                titulo=livro['titulo'],
                autor=livro['autor'],
                acesso=livro['acesso']
            )
            livrosFisica.append(fisicaObj)

        message = f"Livros que encontrei para o título {titulo} informado: "
        dispatcher.utter_message(text=message)


        if livrosMinhaBiblioteca:
            messageMinhaBiblioteca = f"Na minha biblioteca: "

            dispatcher.utter_message(text=messageMinhaBiblioteca)

            for mbLivro in livrosMinhaBiblioteca:
                textLivro = f"Titulo {mbLivro.titulo}, do autor {mbLivro.autor} e está disponível no link {mbLivro.acesso}"
                dispatcher.utter_message(text=textLivro)
        else:
            messageMinhaBiblioteca = f"Desculpe, na Minha Biblioteca não há livros para esse título."
            dispatcher.utter_message(text=messageMinhaBiblioteca)


        if livrosPearson:
            messagePearson = f"e na biblioteca Pearson: "
            dispatcher.utter_message(text=messagePearson)
            
            for prLivro in livrosPearson:
                textLivro = f"Titulo {prLivro.titulo}, do autor {prLivro.autor} e está disponível no link {prLivro.acesso}"
                dispatcher.utter_message(text=textLivro)
        else:
            messagePearson = f"Desculpe, não foi encontrado nenhum livro com esse nome na biblioteca Pearson"
            dispatcher.utter_message(messagePearson)

        if livrosFisica:
            messageFisica = f"Na biblioteca Física: "
            dispatcher.utter_message(text=messageFisica)
            
            for livroFisica in livrosFisica:
                textLivro = f"Titulo {livroFisica.titulo}, do autor {livroFisica.autor} está disponível em {livroFisica.acesso}"
                dispatcher.utter_message(text=textLivro)
        else:
            messagePearson = f"Desculpe, não foi encontrado nenhum livro com esse nome na biblioteca física"
            dispatcher.utter_message(messagePearson)


        if livrosPearson == [] and livrosMinhaBiblioteca == [] and livrosFisica == []:
            m = "Verifique se foi escrito corretamente"
            dispatcher.utter_message(m)
        else:
            msg = "Caso queira mais opções de livros digite: mais opções"
            dispatcher.utter_message(msg)

        return[SlotSet("nome_autor", None)]
    
class ActionBuscarLivroPorAutor(Action):
    def name(self) -> Text:
        return "action_buscar_livro_por_autor"
    
    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:

        dispatcher.utter_message("Buscando livros...")

        autor = tracker.get_slot("nome_autor")

        responseMinhaBiblioteca = requests.get(f"http://localhost:8080/assistant/books/minha-biblioteca/autor/{autor}")
        responsePearson = requests.get(f"http://localhost:8080/assistant/books/pearson-biblioteca/autor/{autor}")
        responseFisica = requests.get(f"http://localhost:8080/assistant/books/biblioteca-fisica/autor/{autor}")

        dataMinhaBiblioteca = responseMinhaBiblioteca.json()
        livrosMinhaBiblioteca = []

        dataPearson = responsePearson.json()
        livrosPearson = []

        dataFisica = responseFisica.json()
        livrosFisica = []

        for livro in dataMinhaBiblioteca:
            livroObj = LivroObj(
                id=livro['id'],
                autor=livro['autor'],
                titulo=livro['titulo'],
                acesso=livro['acesso']
            )
            livrosMinhaBiblioteca.append(livroObj)

        for livro in dataPearson:
            livroObj = LivroObj(
                id=livro['id'],
                autor=livro['autor'],
                titulo=livro['titulo'],
                acesso=livro['acesso']
            )
            livrosPearson.append(livroObj)

        for livro in dataFisica:
            fisicaObj = LivroObj(
                id=livro['id'],
                titulo=livro['titulo'],
                autor=livro['autor'],
                acesso=livro['acesso']
            )
            livrosFisica.append(fisicaObj)

        message = f"Livros que encontrei para o autor {autor} informado: "
        dispatcher.utter_message(text=message)


        if livrosMinhaBiblioteca:
            messageMinhaBiblioteca = f"Na minha biblioteca: "

            dispatcher.utter_message(text=messageMinhaBiblioteca)

            for mbLivro in livrosMinhaBiblioteca:
                textLivro = f"{mbLivro.titulo}, do autor {mbLivro.autor} e está disponível no link {mbLivro.acesso}"
                dispatcher.utter_message(text=textLivro)

        else:
            messageMinhaBiblioteca = f"Desculpe, na Minha Biblioteca não há livros para esse autor."
            dispatcher.utter_message(text=messageMinhaBiblioteca)


        if livrosPearson:
            messagePearson = f"E na biblioteca Pearson: "
            dispatcher.utter_message(text=messagePearson)
            
            for prLivro in livrosPearson:
                textLivro = f"{prLivro.titulo}, do autor {prLivro.autor} e está disponível no link {prLivro.acesso}"
                dispatcher.utter_message(text=textLivro)
        else:
            messagePearson = f"Desculpe, não foi encontrado nenhum livro para este autor na biblioteca Pearson"
            dispatcher.utter_message(messagePearson)
        
        if livrosFisica:
            messageFisica = f"Na biblioteca física: "
            dispatcher.utter_message(text=messageFisica)
            
            for fisicaLivro in livrosFisica:
                textLivro = f"{fisicaLivro.titulo}, do autor {fisicaLivro.autor} está disponível em {fisicaLivro.acesso}"
                dispatcher.utter_message(text=textLivro)
        else:
            messagePearson = f"Desculpe, não foi encontrado nenhum livro para este autor na biblioteca física"
            dispatcher.utter_message(messagePearson)

        if livrosPearson == [] and livrosMinhaBiblioteca == [] and livrosFisica == []:
            m = "Verifique se foi escrito corretamente"
            dispatcher.utter_message(m)
        
        return[SlotSet("titulo_livro", None)]
        

class ActionExtraiNomeDeLivro(Action):
    def name(self) -> Text:
        return "action_extrai_nome_de_livro_ou_nome_de_autor"
    
    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:
        
        nlp = spacy.load('../python/tunned-model-md-v4')
        mensagem = tracker.latest_message.get('text', '')
        doc = nlp(mensagem)

        sentences = [ent.text for ent in doc.ents if ent.label_ == 'BOOK']
        autor = [ent.text for ent in doc.ents if ent.label_ == 'PER']

        if sentences:
            for s in sentences:
                return [SlotSet("titulo_livro", s)]
            return []
        elif autor:
            for a in autor:
                return [SlotSet("nome_autor", a)]
            return []
        else:
            dispatcher.utter_message("Informe o nome do livro ou do autor que você quer")
            return []  


class ActionBuscarMaisOpcoes(Action):
    def name(self) -> Text:
        return "action_buscar_mais_opcoes"
    
    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:
        
        print("Buscando mais opções")
    
        session_id = tracker.get_slot('session_id')

        response = requests.get(f"http://localhost:8080/assistant/{session_id}/books/mais-opcoes")

        data = response.json()
        livros = []

        for livro in data:
            livroObj = LivroObj(
                id=livro['id'],
                autor=livro['autor'],
                titulo=livro['titulo'],
                acesso=livro['acesso']
            )
            livros.append(livroObj)

        if livros:
            mensagem = f"Mais opções de livros: "

            dispatcher.utter_message(mensagem)

            for livro in livros:
                textLivro = f"{livro.titulo}, do autor {livro.autor} e está disponível no link {livro.acesso}"
                dispatcher.utter_message(text=textLivro)
        else:
            dispatcher.utter_message("Desculpe, houve um erro.")

        return []