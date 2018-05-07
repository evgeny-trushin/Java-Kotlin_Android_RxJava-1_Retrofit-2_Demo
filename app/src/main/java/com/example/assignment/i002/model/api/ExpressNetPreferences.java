package com.example.assignment.i002.model.api;

import com.example.assignment.helpers.preferences.PreferencesManagerOps;

public enum ExpressNetPreferences implements PreferencesManagerOps {
        DATA, EXPIRED_DATE;
        Object value = null;
        public void setValue(Object value) {
            this.value = value;
        }
        public Object getValue() {
            return value;
        }
}
