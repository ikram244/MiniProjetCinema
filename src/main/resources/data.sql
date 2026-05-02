INSERT INTO films (titre, genre, duree_min, image_url) VALUES
                                                           ('Inception',      'Science-Fiction', 148, 'https://image.tmdb.org/t/p/w500/oYuLEt3zVCKq57qu2F8dT7NIa6f.jpg'),
                                                           ('Le Roi Lion',    'Animation',       118, 'https://images.affiches-et-posters.com//albums/3/5138/medium/EB38724.jpg'),
                                                           ('Interstellar',   'Science-Fiction', 169, 'https://image.tmdb.org/t/p/w500/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg'),
                                                           ('La La Land',     'Comédie',         128, 'https://image.tmdb.org/t/p/w500/uDO8zWDhfWwoFdKS4fzkUJt0Rf0.jpg'),
                                                           ('Get Out',        'Horreur',         104, 'https://image.tmdb.org/t/p/w500/tFXcEccSQMf3lfhfXKSU9iRBpa3.jpg');

INSERT INTO seances (date_heure, salle, version, capacite, film_id) VALUES
                                                                        ('2026-05-02 14:00:00', 'Salle 1', 'VF',   120, 1),
                                                                        ('2026-05-02 17:30:00', 'Salle 2', 'VOST', 100, 1),
                                                                        ('2026-05-03 10:00:00', 'Salle 1', 'VF',   150, 2),
                                                                        ('2026-05-03 15:00:00', 'Salle 3', 'VF',    80, 3),
                                                                        ('2026-05-04 20:00:00', 'Salle 2', 'VOST', 100, 4),
                                                                        ('2026-05-05 19:30:00', 'Salle 1', 'VF',   120, 5);

INSERT INTO billets (prix, statut, date_achat, seance_id) VALUES
                                                              (12.50, 'VENDU',   '2026-04-28', 1),
                                                              (12.50, 'VENDU',   '2026-04-28', 1),
                                                              (10.00, 'RESERVE', '2026-04-29', 1),
                                                              (14.00, 'VENDU',   '2026-04-27', 2),
                                                              (14.00, 'ANNULE',  '2026-04-25', 2),
                                                              (8.50,  'VENDU',   '2026-04-30', 3),
                                                              (8.50,  'VENDU',   '2026-04-30', 3),
                                                              (11.00, 'RESERVE', '2026-04-29', 4),
                                                              (13.50, 'VENDU',   '2026-04-28', 5),
                                                              (13.50, 'VENDU',   '2026-04-28', 5);