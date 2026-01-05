CREATE TABLE IF NOT EXISTS task_attributes (
    task_id UUID NOT NULL,
    attribute_id UUID NOT NULL,
    PRIMARY KEY (task_id, attribute_id),
    CONSTRAINT fk_task_attributes_task FOREIGN KEY (task_id) REFERENCES tasks(id) ON DELETE CASCADE,
    CONSTRAINT fk_task_attributes_attribute FOREIGN KEY (attribute_id) REFERENCES attributes(id) ON DELETE CASCADE
);

CREATE INDEX idx_task_attributes_task_id ON task_attributes(task_id);
CREATE INDEX idx_task_attributes_attribute_id ON task_attributes(attribute_id);