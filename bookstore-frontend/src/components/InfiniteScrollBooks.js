import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Grid, CircularProgress, Box } from '@mui/material';
import InfiniteScroll from 'react-infinite-scroll-component';
import { fetchBooks } from '../redux/actions';
import BookCard from './BookCard';

const InfiniteScrollBooks = () => {
    const dispatch = useDispatch();
    const { data, loading, page, hasMore } = useSelector((state) => state.books);

    useEffect(() => {
        if (page === 0) {
            dispatch(fetchBooks(0));
        }
    }, [dispatch, page]);

    const fetchMoreBooks = () => {
        if (!loading && hasMore) {
            dispatch(fetchBooks(page));
        }
    };

    return (
        <Box sx={{ minHeight: '100vh', overflow: 'hidden' }}>
            <InfiniteScroll
                dataLength={data.length}
                next={fetchMoreBooks}
                hasMore={hasMore}
                loader={<div style={{ textAlign: 'center' }}><CircularProgress /></div>}
                endMessage={<p style={{ textAlign: 'center' }}><b>No more books</b></p>}
            >
                <div className="book-grid">
                    {data.map((book) => (
                        <BookCard key={book.id} book={book} />
                    ))}
                </div>
            </InfiniteScroll>
        </Box>
    );
};

export default InfiniteScrollBooks;