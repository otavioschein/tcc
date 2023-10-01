from typing import Any, Text, Dict, List
from rasa_sdk import Action, Tracker
from rasa_sdk.executor import CollectingDispatcher
from rasa_sdk.events import SlotSet
import requests
import spacy

class LivroObj:
    def __init__(self, id, autor, titulo, biblioteca, urlAcesso):
        self.id = id
        self.autor = autor
        self.titulo = titulo
        self.biblioteca = biblioteca
        self.urlAcesso = urlAcesso

class ActionBuscarLivroPorTitulo(Action):
    def name(self) -> Text:
        return "action_buscar_livro_por_titulo"
    
    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:

        titulo = tracker.get_slot("titulo_livro")

        responseMinhaBiblioteca = requests.get(f"http://54.92.174.226:8080/assistant/books/minha-biblioteca/titulo/{titulo}")
        responsePearson = requests.get(f"http://54.92.174.226:8080/assistant/books/pearson-biblioteca/titulo/{titulo}")
        responseFisica = requests.get(f"http://54.92.174.226:8080/assistant/books/biblioteca-fisica/titulo/{titulo}")

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
                biblioteca=livro['biblioteca'],
                urlAcesso=livro['urlAcesso']
            )
            livrosMinhaBiblioteca.append(livroObj)

        for livro in dataPearson:
            livroObj = LivroObj(
                id=livro['id'],
                autor=livro['autor'],
                titulo=livro['titulo'],
                biblioteca=livro['biblioteca'],
                urlAcesso=livro['urlAcesso']
            )
            livrosPearson.append(livroObj)

        for livro in dataFisica:
            fisicaObj = LivroObj(
                id=livro['id'],
                autor=livro['autor'],
                titulo=livro['titulo'],
                biblioteca=livro['biblioteca']
            )
            livrosFisica.append(fisicaObj)

        message = f"Livros que encontrei para o título {titulo} informado: "
        dispatcher.utter_message(text=message)


        if livrosMinhaBiblioteca:
            messageMinhaBiblioteca = f"Na minha biblioteca: "

            dispatcher.utter_message(text=messageMinhaBiblioteca)

            for mbLivro in livrosMinhaBiblioteca:
                textLivro = f"Titulo {mbLivro.titulo}, do autor {mbLivro.autor} e está disponível no link {mbLivro.urlAcesso}"
                dispatcher.utter_message(text=textLivro)
        else:
            messageMinhaBiblioteca = f"Desculpe, na Minha Biblioteca não há livros para esse título."
            dispatcher.utter_message(text=messageMinhaBiblioteca)


        if livrosPearson:
            messagePearson = f"e na biblioteca Pearson: "
            dispatcher.utter_message(text=messagePearson)
            
            for prLivro in livrosPearson:
                textLivro = f"Titulo {prLivro.titulo}, do autor {prLivro.autor} e está disponível no link {prLivro.urlAcesso}"
                dispatcher.utter_message(text=textLivro)
        else:
            messagePearson = f"Desculpe, não foi encontrado nenhum livro com esse nome na biblioteca Pearson"
            dispatcher.utter_message(messagePearson)

        if livrosFisica:
            messageFisica = f"Na biblioteca Física: "
            dispatcher.utter_message(text=messageFisica)
            
            for livroFisica in livrosFisica:
                textLivro = f"Titulo {livroFisica.titulo}, do autor {livroFisica.autor} está disponível"
                dispatcher.utter_message(text=textLivro)
        else:
            messagePearson = f"Desculpe, não foi encontrado nenhum livro com esse nome na biblioteca física"
            dispatcher.utter_message(messagePearson)


        if livrosPearson == [] and livrosMinhaBiblioteca == [] and livrosFisica == []:
            m = "Verifique se foi escrito corretamente"
            dispatcher.utter_message(m)

        return[SlotSet("nome_autor", None)]
    
class ActionBuscarLivroPorAutor(Action):
    def name(self) -> Text:
        return "action_buscar_livro_por_autor"
    
    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:

        autor = tracker.get_slot("nome_autor")

        responseMinhaBiblioteca = requests.get(f"http://54.92.174.226:8080/assistant/books/minha-biblioteca/autor/{autor}")
        responsePearson = requests.get(f"http://54.92.174.226:8080/assistant/books/pearson-biblioteca/autor/{autor}")
        responseFisica = requests.get(f"http://54.92.174.226:8080/assistant/books/biblioteca-fisica/autor/{autor}")

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
                biblioteca=livro['biblioteca'],
                urlAcesso=livro['urlAcesso']
            )
            livrosMinhaBiblioteca.append(livroObj)

        for livro in dataPearson:
            livroObj = LivroObj(
                id=livro['id'],
                autor=livro['autor'],
                titulo=livro['titulo'],
                biblioteca=livro['biblioteca'],
                urlAcesso=livro['urlAcesso']
            )
            livrosPearson.append(livroObj)

        for livro in dataFisica:
            fisicaObj = LivroObj(
                id=livro['id'],
                autor=livro['autor'],
                titulo=livro['titulo'],
                biblioteca=livro['biblioteca']
            )
            livrosFisica.append(fisicaObj)

        message = f"Livros que encontrei para o autor {autor} informado: "
        dispatcher.utter_message(text=message)


        if livrosMinhaBiblioteca:
            messageMinhaBiblioteca = f"Na minha biblioteca: "

            dispatcher.utter_message(text=messageMinhaBiblioteca)

            for mbLivro in livrosMinhaBiblioteca:
                textLivro = f"{mbLivro.titulo}, do autor {mbLivro.autor} e está disponível no link {mbLivro.urlAcesso}"
                dispatcher.utter_message(text=textLivro)

        else:
            messageMinhaBiblioteca = f"Desculpe, na Minha Biblioteca não há livros para esse autor."
            dispatcher.utter_message(text=messageMinhaBiblioteca)


        if livrosPearson:
            messagePearson = f"E na biblioteca Pearson: "
            dispatcher.utter_message(text=messagePearson)
            
            for prLivro in livrosPearson:
                textLivro = f"{prLivro.titulo}, do autor {prLivro.autor} e está disponível no link {prLivro.urlAcesso}"
                dispatcher.utter_message(text=textLivro)
        else:
            messagePearson = f"Desculpe, não foi encontrado nenhum livro para este autor na biblioteca Pearson"
            dispatcher.utter_message(messagePearson)
        
        if livrosFisica:
            messageFisica = f"Na biblioteca física: "
            dispatcher.utter_message(text=messageFisica)
            
            for fisicaLivro in livrosFisica:
                textLivro = f"{fisicaLivro.titulo}, do autor {fisicaLivro.autor} está disponível"
                dispatcher.utter_message(text=textLivro)
        else:
            messagePearson = f"Desculpe, não foi encontrado nenhum livro para este autor na biblioteca física"
            dispatcher.utter_message(messagePearson)

        if livrosPearson == [] and livrosMinhaBiblioteca == [] and livrosFisica == []:
            m = "Verifique se foi escrito corretamente"
            dispatcher.utter_message(m)
        
        return[SlotSet("titulo_livro", None)]
    

class ActionBuscarLivrosSemelhantes(Action):
    def name(self) -> Text:
        return "action_buscar_livros_semelhantes"
    
    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:
        
        titulo = tracker.get_slot("titulo_livro")
        autor = tracker.get_slot("nome_autor")

        if titulo != None:
            responseMinhaBiblioteca = requests.get(f"http://54.92.174.226:8080/assistant/books/minha-biblioteca/titulo/semelhante/{titulo}")
            responsePearson = requests.get(f"http://54.92.174.226:8080/assistant/books/pearson-biblioteca/titulo/semelhante/{titulo}")
            responseFisica = requests.get(f"http://54.92.174.226:8080/assistant/books/biblioteca-fisica/titulo/semelhante/{titulo}")

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
                    biblioteca=livro['biblioteca'],
                    urlAcesso=livro['urlAcesso']
                )
                livrosMinhaBiblioteca.append(livroObj)

            for livro in dataPearson:
                livroObj = LivroObj(
                    id=livro['id'],
                    autor=livro['autor'],
                    titulo=livro['titulo'],
                    biblioteca=livro['biblioteca'],
                    urlAcesso=livro['urlAcesso']
                )
                livrosPearson.append(livroObj)

            for livro in dataFisica:
                fisicaObj = LivroObj(
                    id=livro['id'],
                    autor=livro['autor'],
                    titulo=livro['titulo'],
                    biblioteca=livro['biblioteca']
                )
                livrosFisica.append(fisicaObj)

            message = f"Livros semelhantes para {titulo}: "
            dispatcher.utter_message(text=message)

            if livrosMinhaBiblioteca:
                messageMinhaBiblioteca = f"Na minha biblioteca: "

                dispatcher.utter_message(text=messageMinhaBiblioteca)

                for mbLivro in livrosMinhaBiblioteca:
                    textLivro = f"{mbLivro.titulo}, do autor {mbLivro.autor} e está disponível no link {mbLivro.urlAcesso}"
                    dispatcher.utter_message(text=textLivro)
            else:
                messageMinhaBiblioteca = f"Desculpe, na Minha Biblioteca não há livros para esse título."
                dispatcher.utter_message(text=messageMinhaBiblioteca)

            if livrosPearson:
                messagePearson = f"e na biblioteca Pearson: "
                dispatcher.utter_message(text=messagePearson)
                
                for prLivro in livrosPearson:
                    textLivro = f"{prLivro.titulo}, do autor {prLivro.autor} e está disponível no link {prLivro.urlAcesso}"
                    dispatcher.utter_message(text=textLivro)
            else:
                messagePearson = f"Desculpe, não foi encontrado nenhum livro com esse nome na biblioteca Pearson"
                dispatcher.utter_message(messagePearson)

            if livrosFisica:
                messageFisica = f"Na biblioteca física: "
                dispatcher.utter_message(text=messageFisica)
                
                for fisicaLivro in livrosFisica:
                    textLivro = f"{fisicaLivro.titulo}, do autor {fisicaLivro.autor} está disponível"
                    dispatcher.utter_message(text=textLivro)
            else:
                messagePearson = f"Desculpe, não foi encontrado nenhum livro na biblioteca física"
                dispatcher.utter_message(messagePearson)

            return []
        
        else:
            responseMinhaBiblioteca = requests.get(f"http://54.92.174.226:8080/assistant/books/minha-biblioteca/autor/semelhante/{autor}")
            responsePearson = requests.get(f"http://54.92.174.226:8080/assistant/books/pearson-biblioteca/autor/semelhante/{autor}")
            responseFisica = requests.get(f"http://54.92.174.226:8080/assistant/books/biblioteca-fisica/autor/semelhante/{autor}")

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
                    biblioteca=livro['biblioteca'],
                    urlAcesso=livro['urlAcesso']
                )
                livrosMinhaBiblioteca.append(livroObj)

            for livro in dataPearson:
                livroObj = LivroObj(
                    id=livro['id'],
                    autor=livro['autor'],
                    titulo=livro['titulo'],
                    biblioteca=livro['biblioteca'],
                    urlAcesso=livro['urlAcesso']
                )
                livrosPearson.append(livroObj)

            for livro in dataFisica:
                fisicaObj = LivroObj(
                    id=livro['id'],
                    autor=livro['autor'],
                    titulo=livro['titulo'],
                    biblioteca=livro['biblioteca']
                )
                livrosFisica.append(fisicaObj)

            message = f"Livros semelhantes para o autor {autor}: "
            dispatcher.utter_message(text=message)


            if livrosMinhaBiblioteca:
                messageMinhaBiblioteca = f"Na minha biblioteca: "

                dispatcher.utter_message(text=messageMinhaBiblioteca)

                for mbLivro in livrosMinhaBiblioteca:
                    textLivro = f"{mbLivro.titulo}, do autor {mbLivro.autor} e está disponível no link {mbLivro.urlAcesso}"
                    dispatcher.utter_message(text=textLivro)

            else:
                messageMinhaBiblioteca = f"Desculpe, na Minha Biblioteca não há livros para esse autor."
                dispatcher.utter_message(text=messageMinhaBiblioteca)


            if livrosPearson:
                messagePearson = f"E na biblioteca Pearson: "
                dispatcher.utter_message(text=messagePearson)
                
                for prLivro in livrosPearson:
                    textLivro = f"{prLivro.titulo}, do autor {prLivro.autor} e está disponível no link {prLivro.urlAcesso}"
                    dispatcher.utter_message(text=textLivro)
            else:
                messagePearson = f"Desculpe, não foi encontrado nenhum livro para este autor na biblioteca Pearson"
                dispatcher.utter_message(messagePearson)
            
            if livrosFisica:
                messageFisica = f"Na biblioteca física: "
                dispatcher.utter_message(text=messageFisica)
                
                for fisicaLivro in livrosFisica:
                    textLivro = f"{fisicaLivro.titulo}, do autor {fisicaLivro.autor} está disponível"
                    dispatcher.utter_message(text=textLivro)
            else:
                messagePearson = f"Desculpe, não foi encontrado nenhum livro na biblioteca física"
                dispatcher.utter_message(messagePearson)

            if livrosPearson == [] and livrosMinhaBiblioteca == []:
                m = "Verifique se foi escrito corretamente"
                dispatcher.utter_message(m)
            
            return[]

class ActionExtrairNomeDeLivroNoInput(Action):
    def name(self) -> Text:
        return "action_extrair_nome_de_livro_no_input"
    
    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:
        
        nlp = spacy.load('pt_core_news_sm')
        mensagem = tracker.latest_message.get('text', '')
        doc = nlp(mensagem)
        
        sentences = [ent.text for ent in doc.ents if ent.label_ == 'titulo_livro']

        if sentences:
            livro = sentences[0]
            return [SlotSet("titulo_livro", livro)]
        else:
            dispatcher.utter_message("Desculpe, não entendi.")
        

class ActionExtrairNomeDeAutorNoInput(Action):
    def name(self) -> Text:
        return "action_extrair_nome_de_autor_no_input"
    
    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:
        
        nlp = spacy.load('pt_core_news_sm')
        mensagem = tracker.latest_message.get('text', '')
        doc = nlp(mensagem)
        
        autor = [ent.text for ent in doc.ents if ent.label_ == 'PER']

        for a in autor:
            return [SlotSet("nome_autor", a)]
        