import React, { useEffect, useState } from 'react';
import { DataGrid, Column, Scrolling, Paging } from 'devextreme-react/data-grid';
import 'devextreme/dist/css/dx.light.css';
import axios from 'axios';
import BookPopup from './BookPopup';

const BookTable = () => {
    const [books, setBooks] = useState([]);
    const [loading, setLoading] = useState(true);
    const [page, setPage] = useState(0);
    const [hasMore, setHasMore] = useState(true);
    const [popupVisible, setPopupVisible] = useState(false);
    const [selectedBook, setSelectedBook] = useState(null);

    useEffect(() => {
        fetchBooks();
    }, []);

    const fetchBooks = async () => {
        setLoading(true);
        try {
            const response = await axios.get(`/api/books?pageNumber=${page}&pageSize=50`);
            const newBooks = response.data;
            setBooks(prevBooks => [...prevBooks, ...newBooks]);
            setHasMore(newBooks.length > 0);
            setPage(prevPage => prevPage + 1);
        } catch (error) {
            console.error('Error fetching books:', error);
        }
        setLoading(false);
    };

    const onRowClick = (e) => {
        setSelectedBook(e.data);
        setPopupVisible(true);
    };

    const imageColumnTemplate = (data) => {
        const imageUrl = data.value;
        if (imageUrl) {
            return <img src={imageUrl} alt={data.data.title} style={{ width: '150px', height: '150px' }} />;
        } else {
            return <div>Image not available</div>;
        }
    };

    return (
        <div>
            <DataGrid
                dataSource={books}
                showBorders={true}
                remoteOperations={true}
                allowColumnReordering={true}
                allowColumnResizing={true}
                columnAutoWidth={true}
                wordWrapEnabled={true}
                loadPanel={{ enabled: true }}
                onRowClick={onRowClick}
                hoverStateEnabled={true} // Включаем изменение курсора при наведении
            >
                <Scrolling mode="virtual" />
                <Paging defaultPageSize={50} />
                <Column dataField="imageUrl" caption="Image" cellRender={imageColumnTemplate} />
                <Column dataField="author" caption="Author" dataType="string" />
                <Column dataField="title" caption="Title" dataType="string" />
                <Column dataField="year" caption="Year" dataType="number" />
                <Column dataField="price" caption="Price" dataType="number" format="currency" />
            </DataGrid>
            {popupVisible && selectedBook && (
                <BookPopup
                    book={selectedBook}
                    visible={popupVisible}
                    onClose={() => setPopupVisible(false)}
                />
            )}
        </div>
    );
};

export default BookTable;