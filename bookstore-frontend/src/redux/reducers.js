import { combineReducers } from 'redux';
import {
    FETCH_BOOKS_REQUEST,
    FETCH_BOOKS_SUCCESS,
    FETCH_BOOKS_FAILURE,
    LOGIN_SUCCESS,
    LOGOUT
} from './actionTypes';

const initialBooksState = {
    loading: false,
    data: [],
    error: null,
    page: 0,
    hasMore: true,
};

const books = (state = initialBooksState, action) => {
    switch (action.type) {
        case FETCH_BOOKS_REQUEST:
            return { ...state, loading: true };
        case FETCH_BOOKS_SUCCESS:
            const newBooks = action.payload;
            return {
                ...state,
                loading: false,
                data: [...state.data, ...newBooks],
                page: state.page + 1,
                hasMore: newBooks.length > 0 // Если новых книг нет, установите hasMore в false
            };
        case FETCH_BOOKS_FAILURE:
            return { ...state, loading: false, error: action.error };
        default:
            return state;
    }
};

const auth = (state = { token: null }, action) => {
    switch (action.type) {
        case LOGIN_SUCCESS:
            return { ...state, token: action.payload };
        case LOGOUT:
            return { ...state, token: null };
        default:
            return state;
    }
};

const rootReducer = combineReducers({
    books,
    auth
});

export default rootReducer;