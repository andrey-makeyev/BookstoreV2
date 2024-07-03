import React, { useEffect, useState } from 'react';
import { DataGrid, Column, Scrolling, Paging, Editing, Popup, Form, RequiredRule } from 'devextreme-react/data-grid';
import 'devextreme/dist/css/dx.light.css';
import axios from 'axios';

const BookTable = () => {
    const [books, setBooks] = useState([]);
    const [loading, setLoading] = useState(true);
    const [page, setPage] = useState(0);
    const [hasMore, setHasMore] = useState(true);
    const [isAdmin, setIsAdmin] = useState(false); // Add logic to determine if the user is admin

    useEffect(() => {
        fetchBooks();
    }, []);

    const fetchBooks = async () => {
        setLoading(true);
        try {
            const response = await axios.get(`/api/books?pageNumber=${page}&pageSize=10`);
            const newBooks = response.data;
            setBooks(prevBooks => [...prevBooks, ...newBooks]);
            setHasMore(newBooks.length > 0);
            setPage(prevPage => prevPage + 1);
        } catch (error) {
            console.error('Error fetching books:', error);
        }
        setLoading(false);
    };

    const onEditingStart = (e) => {
        if (!isAdmin) {
            e.cancel = true;
            alert('Only admin can perform CRUD operations.');
        }
    };

    const onInitNewRow = (e) => {
        if (!isAdmin) {
            e.cancel = true;
            alert('Only admin can perform CRUD operations.');
        }
    };

    const onRowInserting = async (e) => {
        if (isAdmin) {
            try {
                const response = await axios.post('/api/admin/books', e.data);
                e.data.id = response.data.id;
            } catch (error) {
                console.error('Error adding book:', error);
            }
        }
    };

    const onRowUpdating = async (e) => {
        if (isAdmin) {
            try {
                await axios.put(`/api/admin/books/${e.key}`, e.newData);
            } catch (error) {
                console.error('Error updating book:', error);
            }
        }
    };

    const onRowRemoving = async (e) => {
        if (isAdmin) {
            try {
                await axios.delete(`/api/admin/books/${e.key}`);
            } catch (error) {
                console.error('Error deleting book:', error);
            }
        }
    };

    const onRowClick = (e) => {
        // Open popup with detailed book info
        console.log('Clicked book:', e.data);
        // Implement your popup logic here
    };

    const imageColumnTemplate = (data) => {
        if (data.value) {
            return <img src={`/api/book/image/${data.key}`} alt={data.data.title} style={{ width: '50px', height: '50px' }} />;
        }
        return null; // Added missing return statement
    };

    return (
        <DataGrid
            dataSource={books}
            showBorders={true}
            remoteOperations={true}
            allowColumnReordering={true}
            allowColumnResizing={true}
            columnAutoWidth={true}
            wordWrapEnabled={true}
            loadPanel={{ enabled: true }}
            onEditingStart={onEditingStart}
            onInitNewRow={onInitNewRow}
            onRowInserting={onRowInserting}
            onRowUpdating={onRowUpdating}
            onRowRemoving={onRowRemoving}
            onRowClick={onRowClick}
        >
            <Scrolling mode="virtual" />
            <Paging defaultPageSize={10} />
            <Editing
                mode="popup"
                allowUpdating={isAdmin}
                allowDeleting={isAdmin}
                allowAdding={isAdmin}
            />
            <Column dataField="isbn" caption="ISBN">
                <RequiredRule />
            </Column>
            <Column dataField="title" caption="Title">
                <RequiredRule />
            </Column>
            <Column dataField="author" caption="Author">
                <RequiredRule />
            </Column>
            <Column dataField="publisher" caption="Publisher">
                <RequiredRule />
            </Column>
            <Column dataField="year" caption="Year">
                <RequiredRule />
            </Column>
            <Column dataField="description" caption="Description" />
            <Column dataField="price" caption="Price" dataType="number">
                <RequiredRule />
            </Column>
            <Column dataField="image" caption="Image" cellRender={imageColumnTemplate} />
        </DataGrid>
    );
};
