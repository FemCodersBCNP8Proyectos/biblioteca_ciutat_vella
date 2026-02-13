--
-- PostgreSQL database dump
--

-- Dumped from database version 18.1
-- Dumped by pg_dump version 18.1

-- Started on 2026-02-07 08:17:00

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 859 (class 1247 OID 16399)
-- Name: genero; Type: TYPE; Schema: public; Owner: -
--

CREATE TYPE public.genero AS ENUM (
    'ficcion',
    'policiaca',
    'romantica',
    'fantasia',
    'terror',
    'aventuras',
    'psicologia',
    'programacion',
    'infantil',
    'historia',
    'novela'
);


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 221 (class 1259 OID 16428)
-- Name: autor_libro; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.autor_libro (
    autor_id integer CONSTRAINT autor_libro_author_id_not_null NOT NULL,
    libro_id integer NOT NULL
);


--
-- TOC entry 219 (class 1259 OID 16426)
-- Name: autor_libro_author_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.autor_libro_author_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 4942 (class 0 OID 0)
-- Dependencies: 219
-- Name: autor_libro_author_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.autor_libro_author_id_seq OWNED BY public.autor_libro.autor_id;


--
-- TOC entry 220 (class 1259 OID 16427)
-- Name: autor_libro_libro_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.autor_libro_libro_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 4943 (class 0 OID 0)
-- Dependencies: 220
-- Name: autor_libro_libro_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.autor_libro_libro_id_seq OWNED BY public.autor_libro.libro_id;


--
-- TOC entry 223 (class 1259 OID 16438)
-- Name: autores; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.autores (
    id_autor integer NOT NULL,
    nombre character varying NOT NULL
);


--
-- TOC entry 222 (class 1259 OID 16437)
-- Name: autores_id_autor_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.autores_id_autor_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 4944 (class 0 OID 0)
-- Dependencies: 222
-- Name: autores_id_autor_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.autores_id_autor_seq OWNED BY public.autores.id_autor;


--
-- TOC entry 226 (class 1259 OID 16484)
-- Name: libro_generos; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.libro_generos (
    libro_id integer NOT NULL,
    genero public.genero NOT NULL
);


--
-- TOC entry 225 (class 1259 OID 16457)
-- Name: libros; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.libros (
    id_libro integer NOT NULL,
    titulo character varying NOT NULL,
    descripcion character varying NOT NULL,
    isbn character varying NOT NULL
);


--
-- TOC entry 224 (class 1259 OID 16456)
-- Name: libros_id_libro_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.libros_id_libro_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 4945 (class 0 OID 0)
-- Dependencies: 224
-- Name: libros_id_libro_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.libros_id_libro_seq OWNED BY public.libros.id_libro;


--
-- TOC entry 4773 (class 2604 OID 16441)
-- Name: autores id_autor; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.autores ALTER COLUMN id_autor SET DEFAULT nextval('public.autores_id_autor_seq'::regclass);


--
-- TOC entry 4774 (class 2604 OID 16460)
-- Name: libros id_libro; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.libros ALTER COLUMN id_libro SET DEFAULT nextval('public.libros_id_libro_seq'::regclass);


--
-- TOC entry 4776 (class 2606 OID 16436)
-- Name: autor_libro autor_libro_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.autor_libro
    ADD CONSTRAINT autor_libro_pkey PRIMARY KEY (autor_id, libro_id);


--
-- TOC entry 4778 (class 2606 OID 16497)
-- Name: autores autor_unico; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.autores
    ADD CONSTRAINT autor_unico UNIQUE (nombre);


--
-- TOC entry 4780 (class 2606 OID 16447)
-- Name: autores autores_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.autores
    ADD CONSTRAINT autores_pkey PRIMARY KEY (id_autor);


--
-- TOC entry 4786 (class 2606 OID 16490)
-- Name: libro_generos libro_generos_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.libro_generos
    ADD CONSTRAINT libro_generos_pkey PRIMARY KEY (libro_id, genero);


--
-- TOC entry 4782 (class 2606 OID 16514)
-- Name: libros libros_isbn_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.libros
    ADD CONSTRAINT libros_isbn_key UNIQUE (isbn);


--
-- TOC entry 4784 (class 2606 OID 16468)
-- Name: libros libros_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.libros
    ADD CONSTRAINT libros_pkey PRIMARY KEY (id_libro);


--
-- TOC entry 4787 (class 2606 OID 16498)
-- Name: autor_libro fk_autor_libro; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.autor_libro
    ADD CONSTRAINT fk_autor_libro FOREIGN KEY (autor_id) REFERENCES public.autores(id_autor) ON DELETE CASCADE;


--
-- TOC entry 4788 (class 2606 OID 16503)
-- Name: autor_libro fk_id_libro; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.autor_libro
    ADD CONSTRAINT fk_id_libro FOREIGN KEY (libro_id) REFERENCES public.libros(id_libro) ON DELETE CASCADE;


--
-- TOC entry 4789 (class 2606 OID 16508)
-- Name: libro_generos fk_libro_generos; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.libro_generos
    ADD CONSTRAINT fk_libro_generos FOREIGN KEY (libro_id) REFERENCES public.libros(id_libro) ON DELETE CASCADE;


-- Completed on 2026-02-07 08:17:00

--
-- PostgreSQL database dump complete
--

-- INSERT data base Libros
INSERT INTO public.libros (id_libro, titulo, descripcion, isbn) VALUES
(1, 'Comment se faire des amis', 'Una guía práctica sobre cómo relacionarse mejor con los demás: aprender a escuchar, generar empatía, comunicar con respeto e influir positivamente para construir relaciones sólidas y duraderas en cualquier ámbito.', '978-2-253-23871-3'),
(2, 'Never Split the Difference', 'Un enfoque práctico de la negociación basado en técnicas reales del FBI. Enseña a comunicar bajo presión, gestionar emociones y lograr mejores acuerdos sin ceder, aplicable a trabajo, negocios y vida diaria.', '978-0-062-40780-1'),
(3, 'The Pragmatic Programmer', 'Un manual esencial para desarrollar software de forma profesional. Propone buenas prácticas, pensamiento crítico y hábitos sólidos para escribir código mantenible, adaptable y orientado a la mejora continua.', '978-0-135-95705-9'),
(4, 'Las cosas más preciosas.', 'Esta novela es destacada por explorar hasta dónde llegan los personajes por aquellos a quienes aman.', '978-84-08-30960-4'),
(5, 'El monje que vendió su Ferrari', 'Es una obra de desarrollo personal que propone que la verdadera riqueza es la tranquilidad y la realización interior, más allá del éxito material.', '978-84-9908-712-2'),
(6, 'Los Pilares de la Tierra', 'célebre novela histórica ambientada en la Inglaterra del siglo XII, centrada en la construcción de una catedral gótica en el pueblo ficticio de Kingsbridge.', '978-84-9759-290-1'),
(7, 'Historias para leer con sangre fría', 'Esta colección se enfoca en crímenes astutos, misterios de la mente humana y situaciones donde el destino no está marcado, sino que es imprevisible. Se trata de relatos diseñados para el entretenimiento de los amantes del suspense.', '9788422638889'),
(8, 'Mientras la ciudad duerme', 'La obra se ambienta en Nueva Orleans antes de la Guerra de Secesión estadounidense, narrando la ascensión de un joven jugador irlandés, Stephen Fox, en la alta sociedad.', '978-8432088063'),
(9, 'Cien años de soledad', 'Trata sobre la familia Buendía a lo largo de siete generaciones en el pueblo mítico de Macondo, narrando el auge, caída y desaparición del pueblo y la estirpe, marcada por la soledad, el incesto, el realismo mágico, la repetición de patrones históricos y familiares, y eventos extraordinarios que reflejan la historia de Colombia, como guerras y la influencia de compañías extranjeras, concluyendo en un destino cíclico y trágico.', '978-8437604947'),
(10, 'La reina de los condenados', 'Sigue el relato de parte de la historía de Lestat el vampiro en su época de RockStar.', '84-9550145-7'),
(11, 'Un trabajo muy sucio', 'Charlie Asher un dueño de un edificio en San Francisc, tiene una tienda de objetos de segunda mano, le van bien las cosas hasta ue nace su hija.', '978-84-9800-712-1'),
(12, 'El color de la magia', 'Un mundo plano sosteniado por cuatro elefantes impasibles que se apoyan en la espalda de una tortuga gigante donde habitan estrafalarios personajes.', '978-84-9759-679-4')
ON CONFLICT (isbn) DO NOTHING;


-- INSERT data base Autores
INSERT INTO public.autores (id_autor, nombre) VALUES
(1, 'Dale Carnegie'), (2, 'Chris Voss'), (3, 'Andrew Hunt'),
(4, 'Rebecca Yarros'), (5, 'Robin Sharma'), (6, 'Ken Follet'),
(7, 'Alfred Hitchcock'), (8, 'Frank Yerby'), (9, 'Gabriel García Márquez'),
(10, 'Anne Rice'), (11, 'Christopher Moore'), (12, 'Terry Pratchett'), (13, 'David Thomas')
ON CONFLICT (nombre) DO NOTHING;

-- INSERT data base Relación autor_libro (vinculamos el ID del autor con el ID del libro)
INSERT INTO public.autor_libro (autor_id, libro_id) VALUES
(1, 1), (2, 2), (3, 3),(13, 3), (4, 4), (5, 5), (6, 6), 
(7, 7), (8, 8), (9, 9), (10, 10), (11, 11), (12, 12)
ON CONFLICT DO NOTHING;

-- INSERT data base Libro-Géneros
INSERT INTO public.libro_generos (libro_id, genero) VALUES
(1, 'psicologia'), (2, 'psicologia'), (3, 'programacion'), (4, 'romantica'), (5, 'psicologia'),
(6, 'novela'), (7, 'terror'), (8, 'novela'), (9, 'novela'),
(10, 'fantasia'), (11, 'novela'), (12, 'fantasia')
ON CONFLICT DO NOTHING;

-- Actualizar las secuencias
SELECT setval('public.libros_id_libro_seq', (SELECT MAX(id_libro) FROM public.libros));
SELECT setval('public.autores_id_autor_seq', (SELECT MAX(id_autor) FROM public.autores));


-- Comandos de verificación comentados para la primera ejecución

/*
SELECT * FROM libros;

SELECT * FROM autores;

SELECT * FROM autor_libro;

SELECT * FROM libro_generos

TRUNCATE TABLE libro_generos, autor_libro, libros, autores RESTART IDENTITY CASCADE;

SELECT 
    l.id_libro, 
    l.titulo, 
	l.descripcion,
	l.isbn,
    a.nombre AS autor, 
    lg.genero
FROM libros l
JOIN autor_libro al ON l.id_libro = al.libro_id
JOIN autores a ON al.autor_id = a.id_autor
JOIN libro_generos lg ON l.id_libro = lg.libro_id;*/