//
// Created by damtev on 26.12.18.
//

#include "StringParseException.h"

StringParseException::StringParseException(const char* message) : runtime_error(message) {}

StringParseException::StringParseException(const std::string &message) : runtime_error(message) {}