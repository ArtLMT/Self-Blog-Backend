-- Arcs table
CREATE TABLE arcs (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    slug VARCHAR(255) NOT NULL UNIQUE,
    display_order INTEGER NOT NULL,
    start_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    end_date TIMESTAMP WITHOUT TIME ZONE,
    visibility VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by UUID,
    updated_by UUID
);

CREATE INDEX idx_arcs_slug ON arcs(slug);
CREATE INDEX idx_arcs_status ON arcs(status);
CREATE INDEX idx_arcs_display_order ON arcs(display_order);

-- Chapters table
CREATE TABLE chapters (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    arc_id UUID NOT NULL,
    slug VARCHAR(255) NOT NULL UNIQUE,
    order_index INTEGER NOT NULL,
    status VARCHAR(50) NOT NULL,
    reading_time_minutes INTEGER,
    published_at TIMESTAMP WITHOUT TIME ZONE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by UUID,
    updated_by UUID,
    CONSTRAINT fk_chapters_arc FOREIGN KEY (arc_id) REFERENCES arcs(id)
);

CREATE INDEX idx_chapters_arc_id ON chapters(arc_id);
CREATE INDEX idx_chapters_slug ON chapters(slug);
CREATE INDEX idx_chapters_status ON chapters(status);
CREATE INDEX idx_chapters_order_index ON chapters(arc_id, order_index);
