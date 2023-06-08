--formatted liquibase
--changetset oschein:1

INSERT INTO BOOK (NAME, AUTHOR, GENDER, THEME, DESCRIPTION, BOOK_CASE)
VALUES ('Matemática Discreta e Combinatória', 'Ralph P. Grimaldi', 'CIENCIAS', 'matemática',
         'tópicos como teoria dos conjuntos, teoria dos grafos, combinatória e algoritmos', '1-EC-P'),
        ('Cálculo - Volume 2', 'James Stewart', 'CIENCIAS', 'matematica',
         'continuação do primeiro volume e inclui tópicos como integrais, séries e equações diferenciais', '1-EC-E'),
        ('Geometria Diferencial das Curvas e Superfícies', 'Manfredo P. do Carmo', 'CIENCIAS', 'matematica',
         'uma introdução à geometria diferencial, incluindo tópicos como curvatura, superfícies mínimas e teorema de Gauss-Bonnet', '1-EC-D'),
        ('Análise Matemática', 'Tom M. Apostol', 'CIENCIAS', 'matematica',
         'análise real, séries e equações diferenciais ordinárias', '1-LH-I'),
        ('Teoria das Funções de uma Variável', 'João Bosco Pitombeira de Carvalho', 'CIENCIAS', 'matematica',
         'introdução à teoria das funções de uma variável real, incluindo tópicos como limites, continuidade, derivadas e integrais', '2-PP-S'),
        ('Cálculo Avançado', 'James Stewart', 'CIENCIAS', 'matematica',
         'avanço dos conceitos de cálculo, incluindo integrais múltiplas, séries de Fourier e transformadas', '1-EC-E'),
        ('Geometria Analítica', 'Manfredo P. do Carmo', 'CIENCIAS', 'matematica',
         'um estudo da geometria utilizando métodos analíticos, com tópicos como retas, planos e cônicas', '1-EC-C'),
        ('Introdução à Álgebra', 'Ralph P. Grimaldi', 'CIENCIAS', 'matematica',
         'fundamentos da álgebra, incluindo equações lineares, polinômios e sistemas de equações', '1-LH-I'),
        ('Cálculo Vetorial', 'James Stewart', 'CIENCIAS', 'matematica',
         'uma abordagem do cálculo utilizando vetores, incluindo cálculo de campos vetoriais e teorema de Stokes', '2-PP-T'),
        ('Teoria dos Números', 'Ralph P. Grimaldi', 'CIENCIAS', 'matematica',
         'estudo dos números inteiros, incluindo divisibilidade, congruências e criptografia', '2-PP-Z');
