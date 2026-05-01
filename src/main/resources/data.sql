-- =============================================
-- Données de test pour l'application Cinéma
-- src/main/resources/data.sql
-- =============================================

-- Films
INSERT INTO films (titre, genre, duree_min) VALUES ('Inception', 'Science-Fiction', 148);
INSERT INTO films (titre, genre, duree_min) VALUES ('Le Roi Lion', 'Animation', 118);
INSERT INTO films (titre, genre, duree_min) VALUES ('Interstellar', 'Science-Fiction', 169);
INSERT INTO films (titre, genre, duree_min) VALUES ('La La Land', 'Comédie', 128);
INSERT INTO films (titre, genre, duree_min) VALUES ('Get Out', 'Horreur', 104);

-- Séances
INSERT INTO seances (date_heure, salle, version, capacite, film_id) VALUES ('2026-05-02 14:00:00', 'Salle 1', 'VF',   120, 1);
INSERT INTO seances (date_heure, salle, version, capacite, film_id) VALUES ('2026-05-02 17:30:00', 'Salle 2', 'VOST', 100, 1);
INSERT INTO seances (date_heure, salle, version, capacite, film_id) VALUES ('2026-05-03 10:00:00', 'Salle 1', 'VF',   150, 2);
INSERT INTO seances (date_heure, salle, version, capacite, film_id) VALUES ('2026-05-03 15:00:00', 'Salle 3', 'VF',    80, 3);
INSERT INTO seances (date_heure, salle, version, capacite, film_id) VALUES ('2026-05-04 20:00:00', 'Salle 2', 'VOST', 100, 4);
INSERT INTO seances (date_heure, salle, version, capacite, film_id) VALUES ('2026-05-05 19:30:00', 'Salle 1', 'VF',   120, 5);

-- Billets
INSERT INTO billets (prix, statut, date_achat, seance_id) VALUES (12.50, 'VENDU',   '2026-04-28', 1);
INSERT INTO billets (prix, statut, date_achat, seance_id) VALUES (12.50, 'VENDU',   '2026-04-28', 1);
INSERT INTO billets (prix, statut, date_achat, seance_id) VALUES (10.00, 'RESERVE',  '2026-04-29', 1);
INSERT INTO billets (prix, statut, date_achat, seance_id) VALUES (14.00, 'VENDU',   '2026-04-27', 2);
INSERT INTO billets (prix, statut, date_achat, seance_id) VALUES (14.00, 'ANNULE',  '2026-04-25', 2);
INSERT INTO billets (prix, statut, date_achat, seance_id) VALUES (8.50,  'VENDU',   '2026-04-30', 3);
INSERT INTO billets (prix, statut, date_achat, seance_id) VALUES (8.50,  'VENDU',   '2026-04-30', 3);
INSERT INTO billets (prix, statut, date_achat, seance_id) VALUES (11.00, 'RESERVE',  '2026-04-29', 4);
INSERT INTO billets (prix, statut, date_achat, seance_id) VALUES (13.50, 'VENDU',   '2026-04-28', 5);
INSERT INTO billets (prix, statut, date_achat, seance_id) VALUES (13.50, 'VENDU',   '2026-04-28', 5);