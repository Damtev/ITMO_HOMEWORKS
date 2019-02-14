//
// Created by damtev on 26.12.18.
//

#pragma once

#include <exception>
#include <cstring>
#include <stdexcept>

class StringParseException : public std::runtime_error {

public:
	explicit StringParseException(const char* message);

	explicit StringParseException(const std::string& message);
};