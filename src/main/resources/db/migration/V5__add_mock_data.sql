-- Insert Mock Arc 1
INSERT INTO arcs (id, slug, display_order, start_date, end_date, visibility, status, created_at, updated_at) 
VALUES ('a0000000-0000-0000-0000-000000000001', 'my-tech-journey', 1, '2020-01-01 00:00:00', NULL, 'PUBLIC', 'PUBLISHED', now() at time zone 'utc', now() at time zone 'utc');

INSERT INTO arc_translations (arc_id, language, title, summary)
VALUES ('a0000000-0000-0000-0000-000000000001', 'EN', 'My Tech Journey', 'The story of how I became a software engineer.');

-- Insert Mock Chapter 1 for Arc 1
INSERT INTO chapters (id, arc_id, slug, order_index, status, reading_time_minutes, published_at, created_at, updated_at)
VALUES ('c0000000-0000-0000-0000-000000000001', 'a0000000-0000-0000-0000-000000000001', 'early-days', 1, 'PUBLISHED', 15, now() at time zone 'utc', now() at time zone 'utc', now() at time zone 'utc');

INSERT INTO chapter_translations (chapter_id, language, title, quote, summary)
VALUES ('c0000000-0000-0000-0000-000000000001', 'EN', 'The Early Days', 'Every expert was once a beginner.', 'My first encounters with a computer and writing my first lines of code.');

-- Insert Mock Episode 1 for Chapter 1
INSERT INTO episodes (id, chapter_id, slug, order_index, event_date, status, created_at, updated_at)
VALUES ('e0000000-0000-0000-0000-000000000001', 'c0000000-0000-0000-0000-000000000001', 'discovering-programming', 1, '2020-01-15 00:00:00', 'PUBLISHED', now() at time zone 'utc', now() at time zone 'utc');

INSERT INTO episode_translations (episode_id, language, title, markdown_content, rendered_content, conclusion)
VALUES ('e0000000-0000-0000-0000-000000000001', 'EN', 'Discovering Programming', 'It all started when I was playing a video game and wanted to create my own... \n\n```python\nprint("Hello World")\n```', '<p>It all started when I was playing a video game and wanted to create my own...</p><pre><code class="language-python">print(&quot;Hello World&quot;)</code></pre>', 'And that was the beginning of my journey.');

-- Insert Mock Margin Note 1 for Episode 1
INSERT INTO margin_notes (id, episode_id, anchor_position, visibility, created_at, updated_at)
VALUES ('d0000000-0000-0000-0000-000000000001', 'e0000000-0000-0000-0000-000000000001', 'video-game', 'PUBLIC', now() at time zone 'utc', now() at time zone 'utc');

INSERT INTO margin_note_translations (margin_note_id, language, note_content)
VALUES ('d0000000-0000-0000-0000-000000000001', 'EN', 'The game was actually Minecraft, and I was trying to write a mod for it.');

-- Insert Mock Arc 2
INSERT INTO arcs (id, slug, display_order, start_date, end_date, visibility, status, created_at, updated_at) 
VALUES ('a0000000-0000-0000-0000-000000000002', 'life-experiences', 2, '2022-05-01 00:00:00', NULL, 'PUBLIC', 'PUBLISHED', now() at time zone 'utc', now() at time zone 'utc');

INSERT INTO arc_translations (arc_id, language, title, summary)
VALUES ('a0000000-0000-0000-0000-000000000002', 'EN', 'Life Experiences', 'Memorable moments and lessons learned.');
