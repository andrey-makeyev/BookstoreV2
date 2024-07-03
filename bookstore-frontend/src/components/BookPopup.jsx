import React from 'react';
import { Popup } from 'devextreme-react/popup';
import 'devextreme/dist/css/dx.light.css';
import './BookPopup.css'; // Подключаем файл стилей

const BookPopup = ({ book, visible, onClose }) => {
    return (
        <Popup
            visible={visible}
            onHiding={onClose}
            dragEnabled={true}
            closeOnOutsideClick={true}
            showTitle={true}
            title={book.title}
            width="auto"
            height="auto"
            maxWidth="80%" // Ограничиваем максимальную ширину
            maxHeight="80%" // Ограничиваем максимальную высоту
            contentRender={() => (
                <div className="popup-content">
                    <img src={book.imageUrl} alt={book.title} className="popup-image" />
                    <h2>{book.title}</h2>
                    <p><strong>Author:</strong> {book.author}</p>
                    <p><strong>Description:</strong> {book.description}</p>
                    <p><strong>Year:</strong> {book.year}</p>
                    <p><strong>Price:</strong> ${book.price}</p>
                    <div className="popup-footer">
                        <p><strong>Publisher:</strong> {book.publisher}</p>
                        <p><strong>ISBN:</strong> {book.isbn}</p>
                    </div>
                </div>
            )}
        />
    );
};

export default BookPopup;