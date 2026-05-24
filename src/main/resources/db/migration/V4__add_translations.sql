-- Arc Translations
CREATE TABLE arc_translations (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    arc_id UUID NOT NULL,
    language VARCHAR(10) NOT NULL,
    title VARCHAR(255) NOT NULL,
    summary VARCHAR(1000),
    CONSTRAINT fk_arc_trans_arc FOREIGN KEY (arc_id) REFERENCES arcs(id) ON DELETE CASCADE
);

CREATE INDEX idx_arc_trans_arc_id ON arc_translations(arc_id);
CREATE INDEX idx_arc_trans_lang ON arc_translations(language);

-- Chapter Translations
CREATE TABLE chapter_translations (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    chapter_id UUID NOT NULL,
    language VARCHAR(10) NOT NULL,
    title VARCHAR(255) NOT NULL,
    quote VARCHAR(500),
    summary VARCHAR(1000),
    CONSTRAINT fk_chapter_trans_chapter FOREIGN KEY (chapter_id) REFERENCES chapters(id) ON DELETE CASCADE
);

CREATE INDEX idx_chapter_trans_chapter_id ON chapter_translations(chapter_id);
CREATE INDEX idx_chapter_trans_lang ON chapter_translations(language);

-- Episode Translations
CREATE TABLE episode_translations (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    episode_id UUID NOT NULL,
    language VARCHAR(10) NOT NULL,
    title VARCHAR(255) NOT NULL,
    markdown_content TEXT NOT NULL,
    rendered_content TEXT NOT NULL,
    conclusion VARCHAR(1000),
    CONSTRAINT fk_episode_trans_episode FOREIGN KEY (episode_id) REFERENCES episodes(id) ON DELETE CASCADE
);

CREATE INDEX idx_episode_trans_episode_id ON episode_translations(episode_id);
CREATE INDEX idx_episode_trans_lang ON episode_translations(language);

-- Margin Note Translations
CREATE TABLE margin_note_translations (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    margin_note_id UUID NOT NULL,
    language VARCHAR(10) NOT NULL,
    note_content TEXT NOT NULL,
    CONSTRAINT fk_note_trans_note FOREIGN KEY (margin_note_id) REFERENCES margin_notes(id) ON DELETE CASCADE
);

CREATE INDEX idx_note_trans_note_id ON margin_note_translations(margin_note_id);
CREATE INDEX idx_note_trans_lang ON margin_note_translations(language);
