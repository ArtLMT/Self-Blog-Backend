-- Episodes table
CREATE TABLE episodes (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    chapter_id UUID NOT NULL,
    slug VARCHAR(255) NOT NULL UNIQUE,
    order_index INTEGER NOT NULL,
    event_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by UUID,
    updated_by UUID,
    CONSTRAINT fk_episodes_chapter FOREIGN KEY (chapter_id) REFERENCES chapters(id)
);

CREATE INDEX idx_episodes_chapter_id ON episodes(chapter_id);
CREATE INDEX idx_episodes_slug ON episodes(slug);
CREATE INDEX idx_episodes_status ON episodes(status);
CREATE INDEX idx_episodes_event_date ON episodes(event_date);
CREATE INDEX idx_episodes_order_index ON episodes(chapter_id, order_index);

-- Margin Notes table
CREATE TABLE margin_notes (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    episode_id UUID NOT NULL,
    anchor_position VARCHAR(255) NOT NULL,
    visibility VARCHAR(50) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by UUID,
    updated_by UUID,
    CONSTRAINT fk_margin_notes_episode FOREIGN KEY (episode_id) REFERENCES episodes(id)
);

CREATE INDEX idx_margin_notes_episode_id ON margin_notes(episode_id);
