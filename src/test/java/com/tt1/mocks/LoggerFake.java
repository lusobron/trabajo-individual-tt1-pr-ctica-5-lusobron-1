package com.tt1.mocks;

import java.util.*;
import org.slf4j.Logger;
import org.slf4j.Marker;


public class LoggerFake implements Logger {

    public List<String> mensajesInfo = new ArrayList<>();
    public List<String> mensajesWarn = new ArrayList<>();

    @Override
    public void info(String msg) {
        mensajesInfo.add(msg);
    }

    @Override
    public void warn(String msg) {
        mensajesWarn.add(msg);
    }

    // El resto de métodos obligatorios de la interfaz los dejamos vacíos
    @Override public String getName() { return "LoggerFake"; }
    @Override public boolean isTraceEnabled() { return false; }
    @Override public void trace(String msg) {}
    @Override public void trace(String format, Object arg) {}
    @Override public void trace(String format, Object arg1, Object arg2) {}
    @Override public void trace(String format, Object... arguments) {}
    @Override public void trace(String msg, Throwable t) {}
    @Override public boolean isTraceEnabled(Marker marker) { return false; }
    @Override public void trace(Marker marker, String msg) {}
    @Override public void trace(Marker marker, String format, Object arg) {}
    @Override public void trace(Marker marker, String format, Object arg1, Object arg2) {}
    @Override public void trace(Marker marker, String format, Object... argArray) {}
    @Override public void trace(Marker marker, String msg, Throwable t) {}
    @Override public boolean isDebugEnabled() { return false; }
    @Override public void debug(String msg) {}
    @Override public void debug(String format, Object arg) {}
    @Override public void debug(String format, Object arg1, Object arg2) {}
    @Override public void debug(String format, Object... arguments) {}
    @Override public void debug(String msg, Throwable t) {}
    @Override public boolean isDebugEnabled(Marker marker) { return false; }
    @Override public void debug(Marker marker, String msg) {}
    @Override public void debug(Marker marker, String format, Object arg) {}
    @Override public void debug(Marker marker, String format, Object arg1, Object arg2) {}
    @Override public void debug(Marker marker, String format, Object... argArray) {}
    @Override public void debug(Marker marker, String msg, Throwable t) {}
    @Override public boolean isInfoEnabled() { return true; }
    @Override public void info(String format, Object arg) { mensajesInfo.add(format); }
    @Override public void info(String format, Object arg1, Object arg2) { mensajesInfo.add(format); }
    @Override public void info(String format, Object... arguments) { mensajesInfo.add(format); }
    @Override public void info(String msg, Throwable t) { mensajesInfo.add(msg); }
    @Override public boolean isInfoEnabled(Marker marker) { return true; }
    @Override public void info(Marker marker, String msg) { mensajesInfo.add(msg); }
    @Override public void info(Marker marker, String format, Object arg) { mensajesInfo.add(format); }
    @Override public void info(Marker marker, String format, Object arg1, Object arg2) { mensajesInfo.add(format); }
    @Override public void info(Marker marker, String format, Object... arguments) { mensajesInfo.add(format); }
    @Override public void info(Marker marker, String msg, Throwable t) { mensajesInfo.add(msg); }
    @Override public boolean isWarnEnabled() { return true; }
    @Override public void warn(String format, Object arg) { mensajesWarn.add(format); }
    @Override public void warn(String format, Object... arguments) { mensajesWarn.add(format); }
    @Override public void warn(String format, Object arg1, Object arg2) { mensajesWarn.add(format); }
    @Override public void warn(String msg, Throwable t) { mensajesWarn.add(msg); }
    @Override public boolean isWarnEnabled(Marker marker) { return true; }
    @Override public void warn(Marker marker, String msg) { mensajesWarn.add(msg); }
    @Override public void warn(Marker marker, String format, Object arg) { mensajesWarn.add(format); }
    @Override public void warn(Marker marker, String format, Object arg1, Object arg2) { mensajesWarn.add(format); }
    @Override public void warn(Marker marker, String format, Object... arguments) { mensajesWarn.add(format); }
    @Override public void warn(Marker marker, String msg, Throwable t) { mensajesWarn.add(msg); }
    @Override public boolean isErrorEnabled() { return false; }
    @Override public void error(String msg) {}
    @Override public void error(String format, Object arg) {}
    @Override public void error(String format, Object arg1, Object arg2) {}
    @Override public void error(String format, Object... arguments) {}
    @Override public void error(String msg, Throwable t) {}
    @Override public boolean isErrorEnabled(Marker marker) { return false; }
    @Override public void error(Marker marker, String msg) {}
    @Override public void error(Marker marker, String format, Object arg) {}
    @Override public void error(Marker marker, String format, Object arg1, Object arg2) {}
    @Override public void error(Marker marker, String format, Object... argArray) {}
    @Override public void error(Marker marker, String msg, Throwable t) {}

}
