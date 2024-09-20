-- Database: bati_cuisine

-- DROP DATABASE IF EXISTS bati_cuisine;

-- CREATE DATABASE bati_cuisine
--     WITH
--     OWNER = postgres
--     ENCODING = 'UTF8'
--     LC_COLLATE = 'French_France.1252'
--     LC_CTYPE = 'French_France.1252'
--     LOCALE_PROVIDER = 'libc'
--     TABLESPACE = pg_default
--     CONNECTION LIMIT = -1
--     IS_TEMPLATE = False;
-- CREATE TYPE EtatProjet AS ENUM ('en_cours', 'termine', 'annule');
-- CREATE TYPE TypeComposant AS ENUM ('materiau', 'mainoeuvre');
-- CREATE TYPE TypeMainOeuvre AS ENUM ('de_base', 'specialiste');

CREATE TABLE client (
   id SERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    adresse VARCHAR(255) NOT NULL,
    email VARCHAR(255),  -- TODO: not null unique
  telephone VARCHAR(20)    -- TODO: not null unique
 );

-- CREATE TABLE projet (
--     id SERIAL PRIMARY KEY,
--     nom_projet VARCHAR(255) NOT NULL,
--     marge_beneficiaire DOUBLE PRECISION NOT NULL,
--     cout_total DOUBLE PRECISION NOT NULL,
--     etat_projet EtatProjet NOT NULL,
--     client_id INTEGER NOT NULL REFERENCES client(id) ON DELETE CASCADE
-- );

-- CREATE TABLE devis (
--     id SERIAL PRIMARY KEY,
--     montant_estime DOUBLE PRECISION NOT NULL,
--     date_emission DATE NOT NULL,
--     date_validite DATE NOT NULL,
--     accepte BOOLEAN NOT NULL,
--     projet_id INTEGER UNIQUE NOT NULL REFERENCES projet(id) ON DELETE CASCADE
-- );

-- CREATE TABLE Composant (
--     id SERIAL PRIMARY KEY,
--     nom VARCHAR(100) NOT NULL,
--     taux_TVA DOUBLE PRECISION NOT NULL,
--     type_composant TypeComposant NOT NULL,
--     projet_id INTEGER NOT NULL REFERENCES projet(id) ON DELETE CASCADE
-- );

-- CREATE TABLE mainoeuvre (
--     taux_horaire DOUBLE PRECISION,
--     heures_travail DOUBLE PRECISION,
--     productivite_ouvrier DOUBLE PRECISION,
--     type_main_oeuvre TypeMainOeuvre
-- ) INHERITS (Composant);

CREATE TABLE materiau (
    cout_unitaire DOUBLE PRECISION,
    quantite DOUBLE PRECISION,
    cout_transport DOUBLE PRECISION,
    coef_qualite DOUBLE PRECISION
) INHERITS (Composant);