import axios from 'axios';
import {
    FETCH_BOOKS_REQUEST,
    FETCH_BOOKS_SUCCESS,
    FETCH_BOOKS_FAILURE,
    LOGIN_SUCCESS,
    LOGOUT
} from './actionTypes';

// Fetch books action
export const fetchBooks = (page) => async (dispatch) => {
    dispatch({ type: FETCH_BOOKS_REQUEST });
    try {
        const response = await axios.get('/api/books', {
            params: {
                pageNumber: page,
                pageSize: 10,
            }
        });
        const books = response.data;
        dispatch({
            type: FETCH_BOOKS_SUCCESS,
            payload: books,
        });
    } catch (error) {
        dispatch({
            type: FETCH_BOOKS_FAILURE,
            error: error.message,
        });
    }
};

// Login action
export const login = (userName, password) => async (dispatch) => {
    try {
        const response = await axios.post('/auth/login', { userName, password });
        if (response.status === 200) {
            const token = response.data.token;
            dispatch({
                type: LOGIN_SUCCESS,
                payload: token,
            });
            localStorage.setItem('token', token); // Сохраняем токен в localStorage для сохранения состояния между сессиями
        } else {
            throw new Error('Invalid login');
        }
    } catch (error) {
        console.error('Login error:', error.response ? error.response.data : error.message);
        alert(`Login failed: ${error.response ? error.response.data.message : error.message}`);
    }
};

// Logout action
export const logout = () => (dispatch) => {
    dispatch({ type: LOGOUT });
    localStorage.removeItem('token'); // Удаляем токен из localStorage при выходе
};