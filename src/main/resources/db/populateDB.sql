INSERT INTO public.users(login, first_name, last_name, password, role) VALUES
('admin', 'Админ', 'Админов', '$2a$12$xmQNl72q8ryViec59QO9wev9MEz8DZ/KibZ3/e0LubfpvGWVV/5i.', 'ADMIN'),
('user', 'Семен', 'Петров', '$2a$12$bHCm8ofecur1YbAYZ2n7V.xv1omdmSrdCS/dFtJJyCAmGsKFQIrzy', 'USER');

INSERT INTO public.workout(name) VALUES
('Йога'),
('Бег'),
('Велосипед'),
('Теннис'),
('Плавание'),
('Керлинг'),
('Гимнастика');


INSERT INTO public.scheduler(day, "time", workout_id) VALUES
 ('Понедельник', '10:00', 1),
 ('Понедельник', '12:00', 2),
 ('Понедельник', '14:00', 5),
 ('Понедельник', '16:00', 6),
 ('Вторник', '08:00', 3),
 ('Вторник', '14:00', 4),
 ('Вторник', '16:00', 7),
 ('Вторник', '18:00', 6),
 ('Среда', '14:00', 4),
 ('Среда', '16:00', 3),
 ('Среда', '10:00', 7),
 ('Среда', '08:00', 1),
 ('Четверг', '08:00', 2),
 ('Пятница', '08:00', 1),
 ('Пятница', '12:00', 2),
 ('Пятница', '16:00', 3),
 ('Пятница', '18:00', 5);

INSERT INTO public.user_scheduler(user_id, scheduler_id) VALUES
 (2, 1),
 (2, 2);