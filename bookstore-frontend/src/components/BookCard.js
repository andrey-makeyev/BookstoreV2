import React from 'react';
import { Card, CardContent, CardMedia, Typography, CardActions, Button } from '@mui/material';

const BookCard = ({ book }) => {
    return (
        <Card className="book-card">
            <CardMedia
                component="img"
                alt={book.title}
                height="140"
                image={`https://via.placeholder.com/150?text=${book.title}`}
                title={book.title}
            />
            <CardContent>
                <Typography gutterBottom variant="h5" component="div">
                    {book.title}
                </Typography>
                <Typography variant="body2" color="textSecondary" component="p">
                    {book.description}
                </Typography>
            </CardContent>
            <CardActions>
                <Button size="small" color="primary">
                    Learn More
                </Button>
            </CardActions>
        </Card>
    );
};

export default BookCard;