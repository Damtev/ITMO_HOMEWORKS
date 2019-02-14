//
// Created by damtev on 01/09/18.
//

#ifndef INC_10_PATHS_MESSAGE_H
#define INC_10_PATHS_MESSAGE_H

#include <string>

enum Message {
    SUBMIT
};

std::string getMessage(Message message) {
    switch (message) {
        case SUBMIT: return "submit";
    }
}


#endif //INC_10_PATHS_MESSAGE_H